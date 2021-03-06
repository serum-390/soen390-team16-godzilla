package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.data.repositories.SalesContactRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.orders.Order;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
public class OrdersTests {

    private final LocalDate NOW = LocalDate.now();
    private static final LocalDate FUTURE_DATE = LocalDate.of(2021, 7, 21);
    private static final String ORDERS_API = "/api/orders/";

    @Autowired
    WebTestClient webClient;

    @Autowired
    DatabaseClient databaseClient;

    @MockBean
    OrdersRepository orderRepository;

    @MockBean
    SalesContactRepository salesContactRepository;

    /**
     * Tests: GET /api/orders/
     */
    @Test
    @WithMockUser("test")
    void retrieveAllOrdersTest() {
        int numberOfDemoOrders = 9;
        List<Order> ordersCollection = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(
                buildDemoOrderFlux()
                    .doOnNext(ordersCollection::add)
                    .take(numberOfDemoOrders));

        webClient.get()
                .uri(ORDERS_API)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBodyList(Order.class)
                .hasSize(numberOfDemoOrders)
                .isEqualTo(ordersCollection)
                .value(list -> {
                    assertThat(list).isNotNull();
                });

        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Tests: {@code POST /api/orders/}, <code>GET /api/orders/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void createOrderTest() {
        Order order = buildDemoOrderFlux().blockFirst();
        when(orderRepository.save(order)).thenReturn(Mono.just(order));
        when(orderRepository.findById(order.getId())).thenReturn(Mono.just(order));

        // Send the item
        webClient.post()
                .uri(ORDERS_API)
                .contentType(APPLICATION_JSON)
                .bodyValue(order)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        // Retrieve the item
        assertGetDemoOrder(order);

        verify(orderRepository, times(1)).save(order);
        verify(orderRepository, times(1)).findById(order.getId());
    }

    /**
     * Tests: <code>GET /api/orders/{id}</code>
     *
     * @param id
     */
    @WithMockUser("test")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getOrderTest(int id) {
        List<Order> collector = new ArrayList<>(id);
        Order order = buildDemoOrderFlux()
                .take(id)
                .doOnNext(collector::add)
                .last()
                .block();
        when(orderRepository.findById(id)).thenReturn(Mono.just(order));
        assertGetDemoOrder(collector.get(id - 1));
        verify(orderRepository, times(1)).findById(id);
    }

    /**
     * Tests: <code>PUT /api/orders/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void updateOrderTest() {
        Order order = buildDemoOrderFlux().blockFirst();
        Order order2 = order
                .withCreatedDate(LocalDate.of(1997, 3, 14))
                .withDeliveryLocation("Some other location")
                .withDueDate(LocalDate.of(2025, 12, 21));

        when(orderRepository.update(
                order2.getCreatedDate(),
                order2.getDueDate(),
                order2.getDeliveryLocation(),
                order2.getOrderType(),
                order2.getId(),
                order2.getStatus(),
                order2.getItems()))
                    .thenReturn(Mono.just(1));

        when(orderRepository.findById(order.getId()))
                .thenReturn(Mono.just(order));

        webClient.put()
                .uri(ORDERS_API + order.getId())
                .contentType(APPLICATION_JSON)
                .bodyValue(order2)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        verify(orderRepository, times(1)).findById(order.getId());
        verify(orderRepository, times(1)).update(
                order2.getCreatedDate(),
                order2.getDueDate(),
                order2.getDeliveryLocation(),
                order2.getOrderType(),
                order2.getId(),
                order2.getStatus(),
                order2.getItems());
    }

    /**
     * Tests: <code>DELETE /api/orders/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void deleteOrderTest() {
        Order order = buildDemoOrderFlux().blockFirst();
        when(orderRepository.deleteById(order.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri(ORDERS_API + order.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    /**
     * Generates {@link Order Orders} with progressively incrementing
     * id values
     *
     * @return An inifinitely long Flux Orders that
     */
    private Flux<Order> buildDemoOrderFlux() {
        class Incrementer {
            int val = 1;
            int next() { return val++; }
        }

        Incrementer idIncrementer = new Incrementer();
        Supplier<Order> buildDemoOrder = () -> Order
                .builder()
                .id(idIncrementer.next())
                .createdDate(NOW)
                .dueDate(FUTURE_DATE)
                .deliveryLocation("Godzilla ERP HQ")
                .orderType("Some really good stuff")
                .status("new")
                .items(new HashMap<String,String>())
                .build();

        return Flux.generate(
                buildDemoOrder::get,
                (state, sink) -> {
                    sink.next(state);
                    return buildDemoOrder.get();
                });
    }

    private void assertGetDemoOrder(Order order) {
        webClient.get()
                .uri(ORDERS_API + order.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Order.class)
                .isEqualTo(order)
                .value(o -> assertThat(o)
                    .isNotNull()
                    .hasNoNullFieldsOrProperties()
                    .hasFieldOrPropertyWithValue("createdDate", NOW)
                    .hasFieldOrPropertyWithValue("dueDate", FUTURE_DATE)
                    .hasSameHashCodeAs(order)
                    .hasToString(order.toString()));
    }
}

package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.LocalDate;
import java.util.ArrayList;
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
import ca.serum390.godzilla.data.repositories.SalesOrderRepository;
import ca.serum390.godzilla.domain.sales.SalesOrder;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
public class SalesTests {

    private final LocalDate NOW = LocalDate.now();
    private static final LocalDate FUTURE_DATE = LocalDate.of(2021, 7, 21);
    private static final String SALES_ORDERS_API = "/api/sales/";

    @Autowired
    WebTestClient webClient;

    @Autowired
    DatabaseClient databaseClient;

    @MockBean
    SalesOrderRepository salesOrderRepository;

    @MockBean
    SalesContactRepository salesContactRepository;

    /**
     * Tests: GET /api/sales/
     */
    @Test
    @WithMockUser("test")
    void retrieveAllSalesOrdersTest() {
        int numberOfDemoOrders = 9;
        List<SalesOrder> ordersCollection = new ArrayList<>();
        when(salesOrderRepository.findAll()).thenReturn(
                buildDemoSalesOrderFlux()
                    .doOnNext(ordersCollection::add)
                    .take(numberOfDemoOrders));

        webClient.get()
                .uri(SALES_ORDERS_API)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBodyList(SalesOrder.class)
                .hasSize(numberOfDemoOrders)
                .isEqualTo(ordersCollection)
                .value(list -> {
                    assertThat(list).isNotNull();
                });

        verify(salesOrderRepository, times(1)).findAll();
    }

    /**
     * Tests: {@code POST /api/sales/}, <code>GET /api/sales/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void createSalesOrderTest() {
        SalesOrder order = buildDemoSalesOrderFlux().blockFirst();
        when(salesOrderRepository.save(order)).thenReturn(Mono.just(order));
        when(salesOrderRepository.findById(order.getId())).thenReturn(Mono.just(order));

        // Send the item
        webClient.post()
                .uri(SALES_ORDERS_API)
                .contentType(APPLICATION_JSON)
                .bodyValue(order)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        // Retrieve the item
        assertGetDemoSalesOrder(order);

        verify(salesOrderRepository, times(1)).save(order);
        verify(salesOrderRepository, times(1)).findById(order.getId());
    }

    /**
     * Tests: <code>GET /api/sales/{id}</code>
     *
     * @param id
     */
    @WithMockUser("test")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getSalesOrderTest(int id) {
        List<SalesOrder> collector = new ArrayList<>(id);
        SalesOrder order = buildDemoSalesOrderFlux()
                .take(id)
                .doOnNext(collector::add)
                .last()
                .block();
        when(salesOrderRepository.findById(id)).thenReturn(Mono.just(order));
        assertGetDemoSalesOrder(collector.get(id - 1));
        verify(salesOrderRepository, times(1)).findById(id);
    }

    /**
     * Tests: <code>PUT /api/sales/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void updateSalesOrderTest() {
        SalesOrder order = buildDemoSalesOrderFlux().blockFirst();
        SalesOrder order2 = order
                .withCreatedDate(LocalDate.of(1997, 3, 14))
                .withDeliveryLocation("Some other location")
                .withDueDate(LocalDate.of(2025, 12, 21));

        when(salesOrderRepository.update(
                order2.getCreatedDate(),
                order2.getDueDate(),
                order2.getDeliveryLocation(),
                order2.getOrderType(),
                order2.getId()))
                    .thenReturn(Mono.just(1));

        when(salesOrderRepository.findById(order.getId()))
                .thenReturn(Mono.just(order));

        webClient.put()
                .uri(SALES_ORDERS_API + order.getId())
                .contentType(APPLICATION_JSON)
                .bodyValue(order2)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        verify(salesOrderRepository, times(1)).findById(order.getId());
        verify(salesOrderRepository, times(1)).update(
                order2.getCreatedDate(),
                order2.getDueDate(),
                order2.getDeliveryLocation(),
                order2.getOrderType(),
                order2.getId());
    }

    /**
     * Tests: <code>DELETE /api/sales/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void deleteSalesOrderTest() {
        SalesOrder order = buildDemoSalesOrderFlux().blockFirst();
        when(salesOrderRepository.deleteById(order.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri(SALES_ORDERS_API + order.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();

        verify(salesOrderRepository, times(1)).deleteById(order.getId());
    }

    /**
     * Generates {@link SalesOrder SalesOrders} with progressively incrementing
     * id values
     *
     * @return An inifinitely long Flux SalesOrders that
     */
    private Flux<SalesOrder> buildDemoSalesOrderFlux() {
        class Incrementer {
            int val = 1;
            int next() { return val++; }
        }

        Incrementer idIncrementer = new Incrementer();
        Supplier<SalesOrder> buildDemoOrder = () -> SalesOrder
                .builder()
                .id(idIncrementer.next())
                .createdDate(NOW)
                .dueDate(FUTURE_DATE)
                .deliveryLocation("Godzilla ERP HQ")
                .orderType("Some really good stuff")
                .build();

        return Flux.generate(
                buildDemoOrder::get,
                (state, sink) -> {
                    sink.next(state);
                    return buildDemoOrder.get();
                });
    }

    private void assertGetDemoSalesOrder(SalesOrder order) {
        webClient.get()
                .uri(SALES_ORDERS_API + order.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(SalesOrder.class)
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

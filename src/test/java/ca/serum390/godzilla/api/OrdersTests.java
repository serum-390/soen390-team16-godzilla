package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.orders.Order;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Tests: /api/orders/
 */
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class OrdersTests {

    private static final LocalDate FUTURE_DATE = LocalDate.of(2021, 7, 21);
    private static final String ORDERS_API = "/api/orders/";
    private final LocalDate NOW = LocalDate.now();

    @MockBean
    OrdersRepository orderRepository;

    WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider restDocProvider) {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocProvider))
                .build();
    }

    /**
     * <b>Tests:</b> <code>GET /api/orders/</code>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void retrieveAllOrdersTest() {
        int n = 9;
        List<Order> collector = new ArrayList<>(n);
        var orders = buildDemoOrderFlux()
                .take(n)
                .doOnNext(collector::add);
        when(orderRepository.findAll()).thenReturn(orders);

        client.get()
                .uri(ORDERS_API)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBodyList(Order.class)
                .hasSize(n)
                .isEqualTo(collector)
                .consumeWith(document("api/orders/all_GET", preprocessResponse(prettyPrint())));

        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Tests:
     * <ul>
     * <li><code>POST /api/orders/{id}</code></li>
     * <li><code>GET /api/orders/{id}</code></li>
     * </ul>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void createOrderTest() {
        Mono<Order> order = buildDemoOrderFlux().shareNext();
        order.subscribe(o -> {
            when(orderRepository
                .save(
                    o.getCreatedDate(),
                    o.getDueDate(),
                    o.getDeliveryLocation(),
                    o.getOrderType(),
                    o.getStatus(),
                    o.getItems()))
                .thenReturn(Mono.just(o));
            when(orderRepository.findById(o.getId())).thenReturn(Mono.just(o));
        });

        // Send the item
        client.post()
                .uri(ORDERS_API)
                .contentType(APPLICATION_JSON)
                .body(order, Order.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(document("api/orders/byid_POST", preprocessRequest(prettyPrint())))
                .isEmpty();

        // Retrieve the item
        assertGetDemoOrder(order);

        // Verify database calls
        StepVerifier.create(order)
                .consumeNextWith(
                    o -> {
                        verify(orderRepository, times(1)).save(
                            o.getCreatedDate(),
                            o.getDueDate(),
                            o.getDeliveryLocation(),
                            o.getOrderType(),
                            o.getStatus(),
                            o.getItems());
                        verify(orderRepository, times(1)).findById(o.getId());
                    })
                .expectComplete()
                .verify();
    }

    /**
     * Tests: <code>GET /api/orders/{id}</code>
     * <p>
     * @param id
     */
    @ParameterizedTest
    @WithMockUser("test")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getOrderTest(int id) {
        Mono<Order> order = buildDemoOrderFlux()
                .take(id)
                .last()
                .doOnNext(o -> when(orderRepository
                    .findById(id))
                    .thenReturn(Mono.just(o)));

        StepVerifier.create(order)
                .consumeNextWith(o -> assertGetDemoOrder(Mono.just(o)))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(orderRepository, times(1)).findById(id);
    }

    /**
     * Tests: <code>DELETE /api/orders/{id}</code>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void deleteOrderTest() {
        Order order = buildDemoOrderFlux().blockFirst();
        when(orderRepository.deleteById(order.getId())).thenReturn(Mono.empty());

        client.delete()
                .uri(ORDERS_API + order.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(document("api/orders/byid_DELETE"))
                .isEmpty();

        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    /**
     * Tests: <code>PUT /api/orders/{id}</code>
     */
    @Test
    @WithMockUser("test")
    void updateOrderTest() {
        List<Order> collector = new ArrayList<>(2);
        Mono<Order> order1 = buildDemoOrderFlux()
                .take(1)
                .last()
                .share()
                .doOnNext(o -> collector.add(0, o))
                .doOnNext(o -> when(orderRepository
                    .findById(o.getId()))
                    .thenReturn(Mono.just(o)));

        Mono<Order> order2 = order1
                .map(o -> o
                    .withCreatedDate(LocalDate.of(1997, 3, 14))
                    .withDeliveryLocation("Some other location")
                    .withDueDate(LocalDate.of(2025, 12, 21)))
                .doOnNext(o -> collector.add(1, o))
                .doOnNext(o ->
                    when(orderRepository.update(
                        o.getCreatedDate(),
                        o.getDueDate(),
                        o.getDeliveryLocation(),
                        o.getOrderType(),
                        o.getId(),
                        o.getStatus(),
                        o.getItems()))
                    .thenReturn(Mono.just(1)));

        verifyPut(order2, order1);
        verifyUpdateAndFindBy(collector.get(0), collector.get(1));
    }

    /**
     * Helper method for: {@link #updateOrderTest()}
     *
     * @param source
     * @param target
     */
    private void verifyPut(Mono<Order> source, Mono<Order> target) {
        StepVerifier.create(target)
                .consumeNextWith(o -> client
                    .put()
                    .uri(ORDERS_API + o.getId())
                    .contentType(APPLICATION_JSON)
                    .body(source, Order.class)
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody()
                    .isEmpty())
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));
    }

    /**
     * Helper method for: {@link #updateOrderTest()}
     *
     * @param found
     * @param updated
     */
    private void verifyUpdateAndFindBy(Order found, Order updated) {
        verify(orderRepository, times(1)).findById(found.getId());
        verify(orderRepository, times(1)).update(
                updated.getCreatedDate(),
                updated.getDueDate(),
                updated.getDeliveryLocation(),
                updated.getOrderType(),
                updated.getId(),
                updated.getStatus(),
                updated.getItems());
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

            int next() {
                return val++;
            }
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
                .items(Map.of())
                .build();


        return Flux.generate(
                buildDemoOrder::get,
                (state, sink) -> {
                    sink.next(state);
                    return buildDemoOrder.get();
                });
    }

    private void assertGetDemoOrder(Mono<Order> order) {
        StepVerifier.create(order)
                .assertNext(o -> client
                    .get()
                    .uri(ORDERS_API + o.getId())
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody(Order.class)
                    .consumeWith(document("api/orders/byid_GET", preprocessResponse(prettyPrint())))
                    .isEqualTo(o)
                    .value(o2 -> assertThat(o2)
                        .isNotNull()
                        .hasNoNullFieldsOrProperties()
                        .hasFieldOrPropertyWithValue("createdDate", NOW)
                        .hasFieldOrPropertyWithValue("dueDate", FUTURE_DATE)
                        .hasSameHashCodeAs(o2)
                        .hasToString(o2.toString())))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));
    }
}

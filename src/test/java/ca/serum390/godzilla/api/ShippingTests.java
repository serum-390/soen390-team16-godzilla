package ca.serum390.godzilla.api;

import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import ca.serum390.godzilla.domain.shipping.Shipping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;


/**
 * Tests: /api/shipping/
 */
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ShippingTests {

    private static final LocalDate DUE_DATE = LocalDate.of(2021, 7, 21);
    private static final LocalDate SHIPPING_DATE = LocalDate.now();

    private static final String API_SHIPPING = "/api/shipping/";


    @MockBean
    ShippingRepository shippingRepository;

    WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider provider) {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(provider))
                .build();
    }

    /**
     * <b>Tests:</b> <code>GET /api/shipping/</code>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void getAllItems() {
        final int numberOfItems = 2;
        Flux<Shipping> items = buildTestItems(1).limitRequest(numberOfItems);
        when(shippingRepository.findAll()).thenReturn(items);
        Mono<List<Shipping>> collected = items.collectList();

        StepVerifier.create(collected)
                .consumeNextWith(l -> client.get()
                        .uri(API_SHIPPING)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(Shipping.class)
                        .consumeWith(document("api/shipping/all_GET", preprocessResponse(prettyPrint())))
                        .hasSize(numberOfItems)
                        .isEqualTo(l)
                        .value(ls -> assertThat(ls)
                                .isNotNull()
                                .doesNotContainNull()
                                .isInstanceOf(Collection.class)
                                .containsAll(l)))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(shippingRepository, times(1)).findAll();
    }

    /**
     * Infinite {@link Flux} of {@link Item} instances
     *
     * @return A never-ending {@link Flux} of shipping items
     */
    private static Flux<Shipping> buildTestItems(int startingId) {

        Function<Integer, Shipping> buildItem = id -> Shipping
                .builder()
                .dueDate(DUE_DATE)
                .shippingDate(SHIPPING_DATE)
                .shippingMethod(Shipping.AIR)
                .shippingPrice(Shipping.getPrice(1, Shipping.AIR))
                .orderID(1)
                .id(id)
                .status(Shipping.NEW)
                .build();

        return Flux.fromStream(() -> Stream.iterate(
                buildItem.apply(startingId),
                i -> true,
                (Shipping i) -> buildItem.apply(i.getId() + 1)));
    }
}


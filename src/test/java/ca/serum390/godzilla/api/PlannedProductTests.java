package ca.serum390.godzilla.api;


import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
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
class PlannedProductTests {

    private static final LocalDate PRODUCTION_DATE = LocalDate.now();

    private static final String API_PLANNING = "/api/planning/";

    @MockBean
    PlannedProductsRepository plannedProductsRepository;

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
     * <b>Tests:</b> <code>GET /api/planning/</code>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void getAllItems() {
        final int numberOfItems = 2;
        Flux<PlannedProduct> items = buildTestItems(1).limitRequest(numberOfItems);
        when(plannedProductsRepository.findAll()).thenReturn(items);
        Mono<List<PlannedProduct>> collected = items.collectList();

        StepVerifier.create(collected)
                .consumeNextWith(l -> client.get()
                        .uri(API_PLANNING)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(PlannedProduct.class)
                        .consumeWith(document("api/planning/all_GET", preprocessResponse(prettyPrint())))
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

        verify(plannedProductsRepository, times(1)).findAll();
    }

    /**
     * Infinite {@link Flux} of {@link Item} instances
     *
     * @return A never-ending {@link Flux} of items with the given name
     */
    private static Flux<PlannedProduct> buildTestItems(int startingId) {

        Function<Integer, PlannedProduct> buildItem = id -> PlannedProduct
                .builder()
                .id(id)
                .orderID(1)
                .productionDate(PRODUCTION_DATE)
                .usedItems(null)
                .status(PlannedProduct.NEW)
                .build();

        return Flux.fromStream(() -> Stream.iterate(
                buildItem.apply(startingId),
                i -> true,
                (PlannedProduct i) -> buildItem.apply(i.getId() + 1)));
    }
}
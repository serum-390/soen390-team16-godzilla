package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class InventoryTests {

    private static final String API_INVENTORY = "/api/inventory/";

    @MockBean
    InventoryRepository inventoryRepository;
    WebTestClient client;

    /**
     * Configure the WebTestClient for use with Spring REST Docs
     *
     * @param context {@link ApplicationContext} to use
     * @param restDocumentation
     */
    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider restDocProvider) {
        this.client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocProvider))
                .build();
    }

    /**
     * <b>Tests:</b> <code>GET /api/inventory/</code>
     * <p>
     */
    @Test
    @WithMockUser("test")
    void getAllItems() {
        final int numberOfItems = 2;
        Flux<Item> items = buildTestItems("item", 1).limitRequest(numberOfItems);
        when(inventoryRepository.findAll()).thenReturn(items);
        Mono<List<Item>> collected = items.collectList();

        StepVerifier.create(collected)
                .consumeNextWith(l -> client.get()
                    .uri(API_INVENTORY)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBodyList(Item.class)
                    .consumeWith(document("api/inventory/all_GET", preprocessResponse(prettyPrint())))
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

        verify(inventoryRepository, times(1)).findAll();
    }

    /**
     * <b>Tests:</b> <code>GET /api/inventory/?name=abc&id=123</code>
     *
     * <p>
     *
     * Tries both query string parameters `name` and `id`
     *
     * @param byId
     */
    @ParameterizedTest
    @WithMockUser("test")
    @ValueSource(booleans = {false, true})
    void getItemByNameTest(boolean byId) {
        assertGetItemByNameOrId(byId);
    }

    private void assertGetItemByNameOrId(boolean testById) {
        final int id = 123;
        final String name = "item";

        Mono<Item> item = buildTestItems(name, id)
                .limitRequest(1)
                .next()
                .doOnNext(i -> when(inventoryRepository.findByName(name)).thenReturn(Flux.just(i)))
                .doOnNext(i -> when(inventoryRepository.findById(id)).thenReturn(Mono.just(i)));

        if (testById) {
            String uri = API_INVENTORY + "?id=" + id;
            assertAndDocumentGetInventory(uri, "api/inventory/byid_GET", item, testById);
            verify(inventoryRepository, times(1)).findById(id);
        } else {
            String uri = API_INVENTORY + "?name=" + name;
            assertAndDocumentGetInventory(uri, "api/inventory/byname_GET", item, testById);
            verify(inventoryRepository, times(1)).findByName(name);
        }
    }

    private void assertAndDocumentGetInventory(String uri,
                                               String documentationDir,
                                               Mono<Item> item, boolean byId) {
        StepVerifier.create(item)
                .consumeNextWith(i -> client.get()
                    .uri(uri)
                    .accept(APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBodyList(Item.class)
                    .consumeWith(document(documentationDir, preprocessResponse(prettyPrint())))
                    .hasSize(1)
                    .contains(i)
                    .value(list -> assertThat(list)
                        .isNotNull()
                        .hasAtLeastOneElementOfType(Item.class)
                        .allMatch(i::equals)
                        .isEqualTo(List.of(i))))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));
    }

    /**
     * Infinite {@link Flux} of {@link Item} instances
     *
     * @param name The name for all of your items
     * @return A never-ending {@link Flux} of items with the given name
     */
    private static Flux<Item> buildTestItems(String name, int startingId) {

        Function<Integer, Item> buildItem = id -> Item
                .builder()
                    .id(id)
                    .itemName(name)
                    .goodType(666)
                    .quantity(89)
                    .buyPrice(467)
                    .sellPrice(555)
                    .location("Montreal, Quebec")
                    .billOfMaterial(null)
                .build();

        return Flux.fromStream(() -> Stream.iterate(
                buildItem.apply(startingId),
                i -> true,
                (Item i) -> buildItem.apply(i.getId() + 1)));
    }
}

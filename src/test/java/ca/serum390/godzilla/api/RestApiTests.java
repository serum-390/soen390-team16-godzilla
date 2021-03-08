package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class RestApiTests {

    @MockBean
    InventoryRepository inventoryRepository;

    @Autowired
    private WebTestClient webClient;

    /**
     * Tests API: `GET /api/inventory/`
     *
     * Tries both query string parameters `name` and `id`
     *
     * @param byId
     */
    @WithMockUser("test")
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void getItemByNameTest(boolean byId) {
        assertGetItemByNameOrId(byId);
    }

    private void assertGetItemByNameOrId(boolean testById) {

        // Name and id for our test Item
        final int id = 123;
        final String name = "item";

        // Mock the InventoryRepository to return our test item
        Item item = buildTestItem(name);
        when(inventoryRepository.findByName(name)).thenReturn(Flux.just(item));
        when(inventoryRepository.findById(id)).thenReturn(Mono.just(item));

        // Construct the URI for the API that we will ping
        String uri = "/api/inventory/"
                   + (testById
                   ? "?id=" + id
                   : "?name=" + name);

        // Send a GET request to the REST API
        invokeGetInventory(uri, item);

        if (testById) {
            verify(inventoryRepository, times(1)).findById(id);
        } else {
            verify(inventoryRepository, times(1)).findByName(name);
        }
    }

    private void invokeGetInventory(String uri, Item item) {
        webClient.get()
                 .uri(uri)
                 .exchange()
                 .expectStatus()
                 .is2xxSuccessful()
                 .expectHeader()
                 .contentType(APPLICATION_JSON)
                 .expectBodyList(Item.class)
                 .hasSize(1)
                 .contains(item)
                 .value(list -> assertThat(list).isNotNull()
                                                .hasAtLeastOneElementOfType(Item.class)
                                                .allMatch(item::equals)
                                                .isEqualTo(List.of(item)));
    }

    private static Item buildTestItem(String name) {
        return Item.builder()
                   .id(123)
                   .itemName(name)
                   .goodType(666)
                   .quantity(89)
                   .buyPrice(467)
                   .sellPrice(555)
                   .location("Montreal, Quebec")
                   .billOfMaterial(new HashMap<Integer,Integer>())
                   .build();
    }
}

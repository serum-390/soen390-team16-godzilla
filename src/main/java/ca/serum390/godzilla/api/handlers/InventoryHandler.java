package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.FunctionalUtils.tryParse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import reactor.core.publisher.Mono;

@Component
public class InventoryHandler {

    /**
     * {@link InventoryRepository}
     */
    private final InventoryRepository items;

    public InventoryHandler(InventoryRepository items) {
        this.items = items;
    }

    /**
     * Insert into the table
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Item.class)
                .flatMap(items::save)
                .flatMap(id -> noContent().build());
    }

    /**
     * Delete element in table by id
     */
    public Mono<ServerResponse> deleteById(ServerRequest req) {
        return tryParse(req.pathVariable("id"))
                .map(id -> items.deleteById(id).flatMap(voyd -> noContent().build()))
                .orElseGet(badRequest()::build);
    }

    /**
     * @param req
     * @return
     */
    public Mono<ServerResponse> getBy(ServerRequest req) {
        Optional<String> name = req.queryParam("name");
        Optional<String> id = req.queryParam("id");

        return name.map(this::queryItemsByName)
                .or(() -> id.map(this::queryItemsById))
                .orElseGet(this::queryAllItems);
    }

    /**
     * updates the items in inventory
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = items.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(this::combineItems, existed, req.bodyToMono(Item.class))
                .cast(Item.class)
                .flatMap(this::updateItem)
                .flatMap(countUpdatedRows -> noContent().build());
    }

    private Mono<Integer> updateItem(Item item) {
        return items.update(
                item.getItemName(),
                item.getLocation(),
                item.getId(),
                item.getBuyPrice(),
                item.getGoodType(),
                item.getQuantity(),
                item.getSellPrice(),
                item.isBillOfMaterial());
    }

    private Item combineItems(Object[] items) {
        Item original = (Item) items[0];
        Item updated = (Item) items[1];
        if (updated != null) {
            original.setBillOfMaterial(updated.isBillOfMaterial());
            original.setGoodType(updated.getGoodType());
            original.setBuyPrice(updated.getBuyPrice());
            original.setLocation(updated.getLocation());
            original.setSellPrice(updated.getSellPrice());
            original.setItemName(updated.getItemName());
            original.setQuantity(updated.getQuantity());
        }
        return original;
    }

    private Mono<ServerResponse> queryItemsByName(String name) {
        return ok().contentType(APPLICATION_JSON)
                .body(items.findByName(name), Item.class);
    }

    private Mono<ServerResponse> queryItemsById(String id) {
        return items.findById(Integer.parseInt(id))
                .flatMap(inventory -> ok().body(Mono.just(inventory), Item.class))
                .switchIfEmpty(notFound().build());
    }

    private Mono<ServerResponse> queryAllItems() {
        return ok().body(items.findAll(), Item.class);
    }
}

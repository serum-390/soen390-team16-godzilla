package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

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
                .flatMap(item -> items.save(item.getItemName(), item.getLocation(), item.getBuyPrice(), item.getGoodType(), item.getQuantity(), item.getSellPrice(), item.getBillOfMaterial()))
                .flatMap(id -> noContent().build());
    }

    /**
     * Delete element in table by id
     */
    public Mono<ServerResponse> deleteById(ServerRequest req) {
        return items.deleteById(
                Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    /**
     * @param req
     * @return
     */
    public Mono<ServerResponse> getBy(ServerRequest req) {
        Optional<String> name = req.queryParam("name");
        Optional<String> id = req.queryParam("id");

        if (name.isPresent()) {
            return queryItemsByName(name.get());
        } else if (id.isPresent()) {
            return queryItemsById(id.get());
        } else {
            return queryAllItems();
        }
    }

    /**
     * updates the items in inventory
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = items.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(
                data -> {
                    Item original = (Item) data[0];
                    Item updated = (Item) data[1];
                    if (updated != null) {
                        original.setBillOfMaterial(updated.getBillOfMaterial());
                        original.setGoodType(updated.getGoodType());
                        original.setBuyPrice(updated.getBuyPrice());
                        original.setLocation(updated.getLocation());
                        original.setSellPrice(updated.getSellPrice());
                        original.setItemName(updated.getItemName());
                        original.setQuantity(updated.getQuantity());
                    }
                    return original;
                },
                existed,
                req.bodyToMono(Item.class)
        ).cast(Item.class)
                .flatMap(item -> items.update(item.getItemName(), item.getLocation(), item.getId(), item.getBuyPrice(), item.getGoodType(), item.getQuantity(), item.getSellPrice(), item.getBillOfMaterial()))
                .flatMap(item -> noContent().build());
    }

    private Mono<ServerResponse> queryItemsByName(String name) {

        return items.findByName(name)
                .collectList()
                .flatMap(l -> l.isEmpty() ? Mono.empty() : Mono.just(l))
                .map(l -> l.size() == 1 ? l.get(0) : l)
                .flatMap(item -> ok().bodyValue(item))
                .switchIfEmpty(Mono.defer(() -> status(404).bodyValue("Item with name : " + name + " does not exist.")));
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

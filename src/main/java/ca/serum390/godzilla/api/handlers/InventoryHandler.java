package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

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
    public Mono<ServerResponse> insert(ServerRequest req) {
        Mono<Item> item = req.bodyToMono(Item.class);
        return item
                .flatMap(it -> items.save(it))
                .flatMap(id -> created(URI.create("/items/" + id)).build());
    }

    /**
     * Get db table inventory
     */
    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ok().body(
                items.findAll(),
                Item.class);
    }

    /**
     * Get db table inventory by id
     */
    public Mono<ServerResponse> getById(ServerRequest req) {
        return ok().contentType(APPLICATION_JSON).bodyValue(items.findById(Integer.parseInt(req.pathVariable("id")))).switchIfEmpty(notFound().build());
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
     * Find by item name
     */
    public Mono<ServerResponse> findByName(ServerRequest req) {
        String name = req.pathVariable("item_name");
        return ok().contentType(APPLICATION_JSON).body(items.findbyName(name), Item.class);
    }

    /**
     *
     */
    public Mono<ServerResponse> getBy(ServerRequest req) {
        Optional<String> name = req.queryParam("name");
        Optional<String> id = req.queryParam("id");

        if (name.isPresent()) {
            // Query by name
            return ok().contentType(APPLICATION_JSON).body(items.findbyName(name.get()), Item.class);
        } else if (id.isPresent()) {
            // Query by id
            return items.findById(Integer.parseInt(id.get()))
                    .flatMap(inventory -> ok().body(Mono.just(inventory), Item.class))
                    .switchIfEmpty(notFound().build());
        }
        return ok().body(
                items.findAll(),
                Item.class);
    }
}

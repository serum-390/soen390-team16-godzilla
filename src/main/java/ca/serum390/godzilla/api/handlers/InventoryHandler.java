package ca.serum390.godzilla.api.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.net.URI;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
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
    public Mono<ServerResponse> insert(ServerRequest req){
        return req.bodyToMono(Item.class)
            .flatMap(items::save)
            .flatMap(id -> created(URI.create("/items/" + id)).build());
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
     *
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

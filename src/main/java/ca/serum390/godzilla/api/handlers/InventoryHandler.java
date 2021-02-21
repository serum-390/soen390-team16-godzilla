package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InventoryHandler {

    /*public Mono<ServerResponse> demoInventory(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(buildDemoInventory());
    }/

    /*private static Map<Object, Object> buildDemoInventory() {
        final String NAME = "name";
        final String TYPE = "type";
        final String IMAGE_URL = "image_url";
        final String DESCRIPTION = "description";

        return map().with("message", "Success. Here is your inventory")
                    .with("inventory", List.of(
                        map().with(NAME, "Bike Wheel")
                             .with(TYPE, "raw-material")
                             .with(IMAGE_URL, "/resources/images/wheel.jpeg")
                             .with(DESCRIPTION, "A bicycle wheel."),
                        map().with(NAME, "Drive Train")
                             .with(TYPE, "raw-material")
                             .with(IMAGE_URL, "/resources/images/drive-train.jpeg")
                             .with(DESCRIPTION, "A gear system and bike chain."),
                        map().with(NAME, "Full Bike")
                             .with(TYPE, "finished-product")
                             .with(IMAGE_URL, "/resources/images/full-bike.jpeg")
                             .with(DESCRIPTION, "A finished bicycle.")
                    ));
    }
    */

    /**
     * {@link InventoryRepository}
     */
    private final InventoryRepository items;

    public InventoryHandler(InventoryRepository items){
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
     * Update item in the table
     */

    /**
     * Get db table inventory
     */
    public Mono<ServerResponse> getAll(ServerRequest req){
        return ok().body(
            items.findAll(),
            Item.class);
    }

    /**
     * Get db table inventory by id 
     */
    public Mono<ServerResponse> getById(ServerRequest req){
        return items.findById(
            Integer.parseInt(req.pathVariable("id")))
            .flatMap(inventory -> ok().body(Mono.just(inventory),Item.class))
            .switchIfEmpty(notFound().build()
        );
    }

    /**
     * Delete element in table by id
     */
    public Mono<ServerResponse> deleteByID(ServerRequest req){
        return items.deleteById(
            Integer.parseInt(req.pathVariable("id")))
            .flatMap(deleted -> noContent().build()
        );
    }

    /**
     * Find by item name
     */
//    public Mono<ServerResponse> FindbyName(ServerRequest req) {
//        String name = "SuperSkill BICYCLE1";
//        return items.FindbyName(name).flatMap(inventory -> ok().body(Mono.just(inventory), Item.class))
//        .switchIfEmpty(notFound().build());
//    }

    /**
     * returns true or false for bill of material.
     */
}

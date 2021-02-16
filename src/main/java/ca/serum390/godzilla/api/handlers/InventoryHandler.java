package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import reactor.core.publisher.Mono;


import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class InventoryHandler {

    public Mono<ServerResponse> demoInventory(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(buildDemoInventory());
    }

    private static Map<Object, Object> buildDemoInventory() {
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
    public Mono<ServerResponse> update(ServerRequest req){
        var existed = items.findById(UUID.fromString(req.pathVariable("id")));
        return Mono.zip(
            data -> {
                Item i = (Item) data[0];
                Item i2 = (Item) data[1];
                if (i2 != null && StringUtils.hasText(i2.getItem_name())){
                    i.setItem_name(i2.getItem_name);
                }
                if (i2 != null && StringUtils.hasText(i2.getGood_type())){
                    i.setGood_type(i2.getGood_type);
                }
                if (i2 != null && StringUtils.hasText(i2.getQuantity())){
                    i.setQuantity(i2.getQuantity);
                }
                if (i2 != null && StringUtils.hasText(i2.getBuy_price())){
                    i.setBuy_price(i2.getBuy_price);
                }
                if (i2 != null && StringUtils.hasText(i2.getSell_price())){
                    i.setSell_price(i2.getSell_price);
                }
                if (i2 != null && StringUtils.hasText(i2.getLocation())){
                    i.setLocation(i2.getLocation);
                }
                if (i2 != null && StringUtils.hasText(i2.getBill_of_material())){
                    i.setBill_of_material(i2.getBill_of_material);
                }
                return g;
            }, 
            existed,
            req.bodyToMono(Item.class)
        ).cast(Item.class)
            .flatMap(inventory -> items.update(item.getId(),
                                                item.getItem_name(),
                                                item.getGood_type(),
                                                item.getQuantity(),
                                                item.getBuy_price(),
                                                item.getSell_price(),
                                                item.getLocation(),
                                                item.getBill_of_material()))
            .flatMap(inventory -> noContent().build());
    }

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
            UUID.fromString(req.pathVariable("id")))
            .flatMap(inventory -> ok().body(Mono.just(inventory),Item.class))
            .switchIfEmpty(notFound().build()
        );
    }

    /**
     * Delete element in table by id
     */
    public Mono<ServerResponse> deleteByID(ServerRequest req){
        return items.deleteById(
            UUID.fromString(req.pathVariable("id")))
            .flatMap(deleted -> noContent().build()
        );
    }
}

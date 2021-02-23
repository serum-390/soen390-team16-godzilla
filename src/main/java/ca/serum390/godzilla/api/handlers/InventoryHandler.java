package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = items.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(
                data -> {
                    Item g = (Item) data[0];
                    Item g2 = (Item) data[1];
                    if (g2 != null && StringUtils.hasText(Boolean.toString(g2.isBillOfMaterial()))) {
                        g.setBillOfMaterial(g2.isBillOfMaterial());
                    }
                    if (g2 != null && StringUtils.hasText(Float.toString(g2.getBuyPrice()))) {
                        g.setBuyPrice(g2.getBuyPrice());
                    }
                    if (g2 != null && StringUtils.hasText(g2.getLocation())) {
                        g.setLocation(g2.getLocation());
                    }
                    if (g2 != null && StringUtils.hasText(Float.toString(g2.getSellPrice()))) {
                        g.setSellPrice(g2.getSellPrice());
                    }
                    if (g2 != null && StringUtils.hasText(g2.getItemName())) {
                        g.setItemName(g2.getItemName());
                    }
                    if (g2 != null && StringUtils.hasText(Integer.toString(g2.getQuantity()))) {
                        g.setQuantity(g2.getQuantity());
                    }

                    return g;
                },
                existed,
                req.bodyToMono(Item.class)
        ).cast(Item.class)
                .flatMap(item -> items.update(item.getItemName(), item.getLocation(), item.getId(), item.getBuyPrice(), item.getGoodType(), item.getQuantity(), item.getSellPrice(), item.isBillOfMaterial()))
                .flatMap(item -> noContent().build());
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

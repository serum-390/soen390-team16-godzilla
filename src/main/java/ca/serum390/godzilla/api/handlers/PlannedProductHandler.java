package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class PlannedProductHandler {

    /**
     * {@link ca.serum390.godzilla.data.repositories.PlannedProductsRepository}
     */
    private final PlannedProductsRepository plannedProducts;

    public PlannedProductHandler(PlannedProductsRepository plannedProducts) {
        this.plannedProducts = plannedProducts;
    }

    /**
     * Insert into the table
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(PlannedProduct.class)
                .flatMap(plannedProducts::save)
                .flatMap(id -> noContent().build());
    }

    /**
     * Delete element in table by id
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return plannedProducts.deleteById(
                Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    /**
     * @param req
     * @return
     */
    public Mono<ServerResponse> getBy(ServerRequest req) {
        Optional<String> status = req.queryParam("status");
        if (status.isPresent()) {
            return ok().body(plannedProducts.findAllByStatus(status.get()), Order.class);
        } else {
            return ok().body(plannedProducts.findAll(), PlannedProduct.class);
        }
    }

    /**
     * updates the planned products
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = plannedProducts.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(
                data -> {
                    PlannedProduct original = (PlannedProduct) data[0];
                    PlannedProduct updated = (PlannedProduct) data[1];
                    if (updated != null) {
                        original.setOrderID(updated.getOrderID());
                        original.setProductionDate(updated.getProductionDate());
                        original.setStatus(updated.getStatus());
                    }
                    return original;
                },
                existed,
                req.bodyToMono(Item.class)
        ).cast(PlannedProduct.class)
                .flatMap(plannedProduct -> plannedProducts.update(plannedProduct.getId(), plannedProduct.getOrderID(), plannedProduct.getProductionDate(), plannedProduct.getStatus()))
                .flatMap(item -> noContent().build());
    }

    /**
     * gets the planned product by id
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> getByID(ServerRequest req) {
        return plannedProducts.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(plannedProduct -> ok().body(Mono.just(plannedProduct), PlannedProduct.class))
                .switchIfEmpty(notFound().build());
    }
}

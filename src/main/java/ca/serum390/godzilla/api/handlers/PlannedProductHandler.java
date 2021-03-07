package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class PlannedProductHandler {

    /**
     * {@link ca.serum390.godzilla.data.repositories.PlannedProductsRepository}
     */
    private final PlannedProductsRepository plannedProducts;
    private final InventoryRepository inventoryRepository;
    private final OrdersRepository ordersRepository;

    public PlannedProductHandler(PlannedProductsRepository plannedProducts, InventoryRepository inventoryRepository, OrdersRepository ordersRepository) {
        this.plannedProducts = plannedProducts;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;
    }

    // preproduction

    public Mono<ServerResponse> validateProduction(ServerRequest req) {
        Mono<Order> orderMono = req.bodyToMono(Order.class);
        AtomicBoolean isOrderReady = new AtomicBoolean(true);
        orderMono.subscribe(
                order -> {
                    System.out.println("Order status is " + order.getStatus());
                    order.getItems().forEach((id, quantity) -> {
                        inventoryRepository.findById(id).subscribe(
                                item -> {
                                    if (quantity <= item.getQuantity()) {
                                        // check inventory for finished item
                                        System.out.println("Item " + item.getItemName() + " is available");
                                    } else {
                                        isOrderReady.set(false);
                                        // check bom for that item
                                    }
                                }
                        );
                    });
                    if (isOrderReady.get()) {
                        ordersRepository.update(order.getId(), "Ready").subscribe();
                    }
                },
                error -> System.out.println(" error retrieving order")
        );
        return noContent().build();
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

    public Mono<ServerResponse> getByID(ServerRequest req) {
        return plannedProducts.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(plannedProduct -> ok().body(Mono.just(plannedProduct), PlannedProduct.class))
                .switchIfEmpty(notFound().build());
    }
}

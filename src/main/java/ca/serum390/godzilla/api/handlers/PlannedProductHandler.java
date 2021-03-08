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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        Order order = req.bodyToMono(Order.class).block();
        AtomicBoolean isOrderReady = new AtomicBoolean(true);
        AtomicBoolean isOrderBlocked = new AtomicBoolean(false);
        System.out.println("Order status is " + order.getStatus());
        Order purchaseOrder = new Order();
        purchaseOrder.setItems(new HashMap<>());
        purchaseOrder.setOrderType("purchase");
        purchaseOrder.setStatus("new");
        purchaseOrder.setCreatedDate(LocalDate.of(2021,1,1));
        purchaseOrder.setDueDate(LocalDate.of(2021,1,1));
        purchaseOrder.setDeliveryLocation("Montreal");


        order.getItems().forEach((id, quantity) -> {
            Item item = inventoryRepository.findById(id).block();
            if (quantity <= item.getQuantity()) {
                // check inventory for finished item
                int newQuantity = item.getQuantity() - quantity;
                System.out.println("Item " + item.getItemName() + " is available");
                inventoryRepository.update(id, newQuantity).subscribe(num -> System.out.println("inventory updated"));

            } else {
                isOrderReady.set(false);
                quantity = quantity - item.getQuantity();
                inventoryRepository.update(id, 0).subscribe(num -> System.out.println("inventory updated with 0"));
                // check bom for that item
                System.out.println("Item " + item.getItemName() + " is not available");
                Integer finalQuantity = quantity;
                item.getBillOfMaterial().forEach((id2, quantity2) -> {
                            Item item2 = inventoryRepository.findById(id2).block();
                            int total_quantity = quantity2 * finalQuantity;
                            if (total_quantity <= item2.getQuantity()) {
                                // check inventory for finished item
                                int newQuantity = item2.getQuantity() - total_quantity;
                                System.out.println("Item " + item2.getItemName() + " is available");
                                inventoryRepository.update(id2, newQuantity).subscribe(num -> System.out.println("inventory updated for bom"));
                            } else {
                                total_quantity = total_quantity - item2.getQuantity();
                                isOrderBlocked.set(true);
                                //update inventory
                                inventoryRepository.update(id2, 0).subscribe(num -> System.out.println("inventory updated with 0 for bom"));
                                // create purchase order this bom item with the number = total-quantity
                                purchaseOrder.getItems().put(id2, total_quantity);
                                System.out.println("purchase order updated");
                            }
                        }
                );

                ordersRepository.save(purchaseOrder.getCreatedDate(), purchaseOrder.getDueDate(), purchaseOrder.getDeliveryLocation()
                        , purchaseOrder.getOrderType(), purchaseOrder.getStatus(), purchaseOrder.getItems()).subscribe
                        (Order -> System.out.println("order is saved"));
                if(isOrderReady.get()){
                    ordersRepository.update(id,"Ready").subscribe(num -> System.out.println("order status updated"));
                }
                else if(isOrderBlocked.get()) {
                    ordersRepository.update(id,"blocked").subscribe(num -> System.out.println("order status updated"));
                }
                else{
                    ordersRepository.update(id,"processing").subscribe(num -> System.out.println("order status updated"));
                }
            }
        });
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

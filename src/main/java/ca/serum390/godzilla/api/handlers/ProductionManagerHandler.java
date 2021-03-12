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
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Component
public class ProductionManagerHandler {

    private final PlannedProductsRepository plannedProducts;
    private final InventoryRepository inventoryRepository;
    private final OrdersRepository ordersRepository;

    private Order order = null;
    private Order purchaseOrder = null;
    private LocalDate productionDate;

    public ProductionManagerHandler(PlannedProductsRepository plannedProducts, InventoryRepository inventoryRepository, OrdersRepository ordersRepository) {
        this.plannedProducts = plannedProducts;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;
    }

    // preproduction  \=month?day?year?
    public Mono<ServerResponse> validateProduction(ServerRequest req) {
        setup(req);

        boolean isOrderReady = true;
        boolean isOrderBlocked = false;
        PlannedProduct plannedProduct;


        // Analyze the items in the order item list and the inventory
        if (order != null && productionDate != null) {

            plannedProduct = new PlannedProduct(productionDate, order.getId());

            for (Map.Entry<Integer, Integer> orderEntry : order.getItems().entrySet()) {

                Integer orderItemID = orderEntry.getKey();
                Integer orderItemQuantity = orderEntry.getValue();
                // get item from inventory
                Item orderItem = inventoryRepository.findById(orderItemID).block();

                if (orderItemQuantity <= orderItem.getQuantity()) {
                    inventoryRepository.update(orderItemID, orderItem.getQuantity() - orderItemQuantity).block();

                } else {
                    isOrderReady = false;

                    // get the quantity available from inventory
                    orderItemQuantity = orderItemQuantity - orderItem.getQuantity();
                    inventoryRepository.update(orderItemID, 0).block();

                    // check bom for that item
                    for (Map.Entry<Integer, Integer> bomEntry : orderItem.getBillOfMaterial().entrySet()) {
                        Integer bomItemID = bomEntry.getKey();
                        Integer bomItemQuantity = bomEntry.getValue();

                        Item bomItem = inventoryRepository.findById(bomItemID).block();

                        int total_quantity = bomItemQuantity * orderItemQuantity;
                        if (total_quantity <= bomItem.getQuantity()) {
                            inventoryRepository.update(bomItemID, bomItem.getQuantity() - total_quantity).block();
                        } else {
                            isOrderBlocked = true;
                            total_quantity = total_quantity - bomItem.getQuantity();
                            //update inventory
                            inventoryRepository.update(bomItemID, 0).block();
                            // create purchase order this bom item with the number = total-quantity
                            purchaseOrder.getItems().put(bomItemID, total_quantity);
                        }
                    }
                }
            }
            updateDB(isOrderReady, isOrderBlocked, plannedProduct);
        }
        return noContent().build();
    }


    private void setup(ServerRequest req) {
        Optional<String> orderID = req.queryParam("id");
        Optional<String> productionDateReq = req.queryParam("date");

        purchaseOrder = new Order(LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 1), "Montreal", "purchase");

        // get the sales order object
        if (orderID.isPresent()) {
            order = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
        }

        // Production Date setup
        if (productionDateReq.isPresent()) {
            productionDate = LocalDate.parse(productionDateReq.get());
        } else {
            productionDate = LocalDate.of(1, 1, 1);
        }

    }

    private void updateDB(boolean isOrderReady, boolean isOrderBlocked, PlannedProduct plannedProduct) {
        if (isOrderReady) {
            ordersRepository.update(order.getId(), "Ready")
                    .subscribe(num -> System.out.println("order status updated"));
        } else {
            ordersRepository.update(order.getId(), isOrderBlocked ? "blocked" : "processing")
                    .subscribe(num -> System.out.println("order status updated"));

            plannedProducts.save(plannedProduct)
                    .subscribe(product -> {
                        System.out.println("production is saved");
                        if (isOrderBlocked) {
                            ordersRepository
                                    .save(purchaseOrder.getCreatedDate(), purchaseOrder.getDueDate(), purchaseOrder.getDeliveryLocation()
                                            , purchaseOrder.getOrderType(), purchaseOrder.getStatus(), purchaseOrder.getItems(), product.getId())
                                    .subscribe(Order -> System.out.println("order is saved"));
                        }
                    });
        }
    }

}

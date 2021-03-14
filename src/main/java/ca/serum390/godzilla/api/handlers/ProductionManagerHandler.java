package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.Inventory.Item;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.util.Events.PurchaseOrderEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Component
public class ProductionManagerHandler {

    //BUGs -> save(object) -> jsonb field is null , save(parameters) -> returns empty mono
    //TODO allow cancelling the production
    // allow editing production date ? or just cancel
    //TODO to cancel -> cancel all the purchase orders, -> return everything taken from inventory
    // phase 1) processing production [no purchase order] phase 2) blocked production [purchase orders]
    // phase 1 cancel ) return all the items in item taken to inventory -> remove production
    // phase 2 cancel ) cancel the purchase orders [ remove them]  -> return all the items in taken to inventory

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PlannedProductsRepository plannedProducts;
    private final InventoryRepository inventoryRepository;
    private final OrdersRepository ordersRepository;

    private Order salesOrder = null;
    private Order purchaseOrder = null;
    private LocalDate productionDate;
    Logger logger;

    public ProductionManagerHandler(ApplicationEventPublisher applicationEventPublisher, PlannedProductsRepository plannedProducts, InventoryRepository inventoryRepository, OrdersRepository ordersRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.plannedProducts = plannedProducts;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;

        logger = Logger.getLogger("EventLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:/Users/yasam/Desktop/ERP/src/main/java/ca/serum390/godzilla/util/Events/eventsLog.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            // the following statement is used to log any messages
            logger.info("My first log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // preproduction  \=month?day?year?
    public Mono<ServerResponse> validateProduction(ServerRequest req) {
        setup(req);

        boolean isOrderReady = true;
        boolean isOrderBlocked = false;
        PlannedProduct plannedProduct;


        // Analyze the items in the order item list and the inventory
        if (salesOrder != null && productionDate != null) {

            plannedProduct = new PlannedProduct(productionDate, salesOrder.getId());

            for (Map.Entry<Integer, Integer> orderEntry : salesOrder.getItems().entrySet()) {

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
                            if (purchaseOrder.getItems().containsKey(bomItemID)) {
                                total_quantity += purchaseOrder.getItems().get(bomItemID);
                            }

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
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
        }

        // Production Date setup
        if (productionDateReq.isPresent()) {
            productionDate = LocalDate.parse(productionDateReq.get());
        } else {
            productionDate = LocalDate.of(1, 1, 1);
        }

    }

    private void updateDB(boolean isOrderReady, boolean isOrderBlocked, PlannedProduct plannedProduct) {
        //TODO create timed events for purchase order and production order
        if (isOrderReady) {
            ordersRepository.update(salesOrder.getId(), "Ready").block();
            System.out.println("order status updated");
        } else {
            ordersRepository.update(salesOrder.getId(), isOrderBlocked ? "blocked" : "processing").block();

            PlannedProduct product = plannedProducts.save(plannedProduct).block();
            purchaseOrder.setProductionID(product.getId());
            if (isOrderBlocked) {
                //     Order order = ordersRepository.save(purchaseOrder.getCreatedDate(), purchaseOrder.getDueDate(), purchaseOrder.getDeliveryLocation(), purchaseOrder.getOrderType(), purchaseOrder.getStatus(), purchaseOrder.getItems(), purchaseOrder.getProductionID()).block();
                Order order = ordersRepository.save(purchaseOrder).block();
                ordersRepository.update(order.getId(), purchaseOrder.getItems()).block();
                System.out.println("event fired");
                PurchaseOrderEvent purchaseOrderEvent = new PurchaseOrderEvent(order.getId());
                applicationEventPublisher.publishEvent(purchaseOrderEvent);
            }
        }
    }
}
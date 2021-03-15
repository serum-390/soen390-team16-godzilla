package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.inventory.Item;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.util.Events.ProductionEvent;
import ca.serum390.godzilla.util.Events.PurchaseOrderEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Component
public class ProductionManagerHandler {

    //TODO allow cancelling the production
    // allow editing production date
    // to cancel -> cancel all the purchase orders, -> return everything taken from inventory
    // phase 1) scheduled production [no purchase order] phase 2) blocked production [purchase orders]
    // phase 1 cancel ) return all the items in item taken to inventory -> remove production/ cancel scheduler -> set the status of sales order to new
    // phase 2 cancel ) cancel the purchase orders [ remove them]  -> return all the items in taken to inventory -> set the status of sales order to new

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PlannedProductsRepository plannedProductsRepository;
    private final InventoryRepository inventoryRepository;
    private final OrdersRepository ordersRepository;
    private static final long SCHEDULE_TIME = System.currentTimeMillis() + 120000;

    private TaskScheduler scheduler;
    private Order salesOrder = null;
    private Order purchaseOrder = null;
    private PlannedProduct plannedProduct = null;
    private LocalDate productionDate = LocalDate.now().plusDays(1);
    private Logger logger;
    private boolean isOrderReady = true;
    private boolean isOrderBlocked = false;

    public ProductionManagerHandler(ApplicationEventPublisher applicationEventPublisher, PlannedProductsRepository plannedProducts, InventoryRepository inventoryRepository, OrdersRepository ordersRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.plannedProductsRepository = plannedProducts;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;
        setupLogger();
    }

    // validates the order for production. takes all the needed finished items and bom items from inventory
    // If all the finished items in the order are available, no production item is created -> order is ready
    public Mono<ServerResponse> validateProduction(ServerRequest req) {
        setup(req);

        // Analyze the items in the order item list and the inventory
        if (salesOrder != null && salesOrder.getStatus().equals(Order.NEW)) {

            plannedProduct = new PlannedProduct(productionDate, salesOrder.getId());
            for (Map.Entry<Integer, Integer> orderEntry : salesOrder.getItems().entrySet()) {

                Integer orderItemID = orderEntry.getKey();
                Integer orderItemQuantity = orderEntry.getValue();

                // get item from inventory TODO change to subscribe
                Item orderItem = inventoryRepository.findById(orderItemID).block();

                if (orderItemQuantity <= orderItem.getQuantity()) {
                    inventoryRepository.updateQuantity(orderItemID, orderItem.getQuantity() - orderItemQuantity).block();
                    plannedProduct.getUsedItems().put(orderItemID, orderItemQuantity);
                } else {
                    isOrderReady = false;
                    if (orderItem.getQuantity() > 0) {
                        plannedProduct.getUsedItems().put(orderItemID, orderItem.getQuantity());
                    }

                    // get the quantity available from inventory
                    orderItemQuantity = orderItemQuantity - orderItem.getQuantity();
                    inventoryRepository.updateQuantity(orderItemID, 0).block();

                    // check bom for that item
                    getBOMItems(orderItem, orderItemQuantity);
                }
            }
            processOrder();
        } else {
            logger.info("The order ID is invalid or it is already in pipeline");
        }
        return noContent().build();
    }


    // cancels the production by setting the production status to canceled,setting the associated sale order status to new
    // and by returning all the taken items from inventory
    public Mono<ServerResponse> cancelProduction(ServerRequest req) {
        Integer productionID = Integer.parseInt(req.pathVariable("id"));
        PlannedProduct plannedProduct = plannedProductsRepository.findById(productionID).block();
        if (plannedProduct != null && !plannedProduct.getStatus().equals(PlannedProduct.COMPLETED)) {
            // set status to cancelled
            plannedProductsRepository.updateStatus(productionID, PlannedProduct.CANCELED).block();
            logger.info("Production " + productionID + " is canceled");
            ordersRepository.updateStatus(plannedProduct.getOrderID(), Order.NEW).block();
            // return all the used items to inventory
            for (Map.Entry<Integer, Integer> entry : plannedProduct.getUsedItems().entrySet()) {
                Integer itemQuantity = entry.getValue();
                Integer itemID = entry.getKey();
                inventoryRepository.addToQuantity(itemID, itemQuantity).block();
                logger.info("item " + itemID + " is returned to inventory");
            }
        }
        return noContent().build();
    }

    // sets up the order id and the production date for production pipeline
    // TODO add exception handling for when the query param values are not parsable
    private void setup(ServerRequest req) {
        Optional<String> orderID = req.queryParam("id");
        Optional<String> productionDateReq = req.queryParam("date");
        plannedProduct = null;
        salesOrder = null;
        purchaseOrder = null;
        productionDate = LocalDate.now().plusDays(1);
        isOrderReady = true;
        isOrderBlocked = false;

        // get the sales order object
        if (orderID.isPresent()) {
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
        }

        // Production Date setup
        if (productionDateReq.isPresent()) {
            LocalDate input = LocalDate.parse(productionDateReq.get());
            if (input.compareTo(LocalDate.now()) < 0) {
                logger.info("invalid production date is entered");
            } else {
                productionDate = input;
            }
        }

        purchaseOrder = new Order(LocalDate.now(), LocalDate.now(), "Montreal", "purchase");
    }

    // takes the available bom items for the finished item in order from inventory
    // for the remaining needed bom items, it includes them in the created purchase order item list
    // the order is blocked if at least one needed bom item is unavailable
    private void getBOMItems(Item orderItem, int orderItemQuantity) {
        for (Map.Entry<Integer, Integer> bomEntry : orderItem.getBillOfMaterial().entrySet()) {
            Integer bomItemID = bomEntry.getKey();
            Integer bomItemQuantity = bomEntry.getValue();

            Item bomItem = inventoryRepository.findById(bomItemID).block();

            int total_quantity = bomItemQuantity * orderItemQuantity;
            if (total_quantity <= bomItem.getQuantity()) {
                inventoryRepository.updateQuantity(bomItemID, bomItem.getQuantity() - total_quantity).block();
                plannedProduct.getUsedItems().put(bomItemID, total_quantity);

            } else {
                isOrderBlocked = true;
                if (bomItem.getQuantity() > 0) {
                    int used_quantity = bomItem.getQuantity();
                    if (plannedProduct.getUsedItems().containsKey(bomItemID)) {
                        used_quantity += plannedProduct.getUsedItems().get(bomItemID);
                    }
                    plannedProduct.getUsedItems().put(bomItemID, used_quantity);
                }
                total_quantity = total_quantity - bomItem.getQuantity();
                //update inventory
                inventoryRepository.updateQuantity(bomItemID, 0).block();

                // create purchase order this bom item with the number = total-quantity
                if (purchaseOrder.getItems().containsKey(bomItemID)) {
                    total_quantity += purchaseOrder.getItems().get(bomItemID);
                }
                purchaseOrder.getItems().put(bomItemID, total_quantity);
            }
        }
    }

    // if order is ready [all finished items in inventory] it sets the status of order to ready
    // otherwise, it sets the status of production to processing, sets the status of production to scheduled or blocked
    // saves the production item in db
    private void processOrder() {
        if (isOrderReady) {
            ordersRepository.updateStatus(salesOrder.getId(), Order.READY).block();
        } else {
            ordersRepository.updateStatus(salesOrder.getId(), Order.PROCESSING).block();
            plannedProduct.setStatus(isOrderBlocked ? PlannedProduct.BLOCKED : PlannedProduct.SCHEDULED);
            PlannedProduct product = plannedProductsRepository.save(plannedProduct.getOrderID(), plannedProduct.getProductionDate(), plannedProduct.getStatus(), plannedProduct.getUsedItems()).block();
            purchaseOrder.setProductionID(product.getId());

            if (isOrderBlocked) {
                schedulePurchaseOrder();
            } else {
                scheduleProduction(product.getId());
            }
        }
    }


    // saves the purchase order in db
    //schedules the purchase order event to 2 minutes after current time
    private void schedulePurchaseOrder() {
        Order order = ordersRepository.save(purchaseOrder.getCreatedDate(), purchaseOrder.getDueDate(), purchaseOrder.getDeliveryLocation(), purchaseOrder.getOrderType(), purchaseOrder.getStatus(), purchaseOrder.getItems(), purchaseOrder.getProductionID()).block();
        // schedule purchase order
        PurchaseOrderEvent purchaseOrderEvent = new PurchaseOrderEvent(order.getId());
        logger.info("purchase order " + order.getId() + " is created");
        Runnable purchaseTask = () -> applicationEventPublisher.publishEvent(purchaseOrderEvent);
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        scheduler.schedule(purchaseTask, new Date(SCHEDULE_TIME));
    }

    // schedules the production event to 2 minutes after current time
    //TODO test wih real time scheduling
    private void scheduleProduction(Integer productionID) {
        ProductionEvent productionEvent = new ProductionEvent(productionID);
        Runnable exampleRunnable = () -> applicationEventPublisher.publishEvent(productionEvent);
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        scheduler.schedule(exampleRunnable, new Date(SCHEDULE_TIME));
    }


    // Setups logger to log production info
    private void setupLogger() {
        logger = Logger.getLogger("EventLog");
        FileHandler fh;
        try {
            File dest = new File("src/main/java/ca/serum390/godzilla/util/Events/eventsLog.log");
            fh = new FileHandler(dest.getAbsolutePath());
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
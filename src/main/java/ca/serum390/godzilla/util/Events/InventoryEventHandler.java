package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

@Component
public class InventoryEventHandler {

    private final PlannedProductsRepository plannedProductsRepository;
    private final OrdersRepository ordersRepository;
    private final InventoryRepository inventoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private TaskScheduler scheduler;

    public InventoryEventHandler(PlannedProductsRepository plannedProductsRepository, OrdersRepository ordersRepository, InventoryRepository inventoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plannedProductsRepository = plannedProductsRepository;
        this.ordersRepository = ordersRepository;
        this.inventoryRepository = inventoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // gets the production item waiting on the items added to inventory
    // takes the items from the inventory and unblocks the production
    // schedules the production
    @EventListener
    public void handleInventoryEvent(InventoryEvent event) {
        Integer productionID = event.getProductionID();
        Integer purchaseID = event.getPurchaseOrderID();
        PlannedProduct plannedProduct = plannedProductsRepository.findById(productionID).block();
        Order purchaseOrder = ordersRepository.findById(purchaseID).block();

        if (plannedProduct != null && plannedProduct.getStatus().equals(PlannedProduct.BLOCKED) && purchaseOrder != null) {
            plannedProductsRepository.updateStatus(productionID, PlannedProduct.SCHEDULED).subscribe();

            for (Map.Entry<Integer, Integer> entry : purchaseOrder.getItems().entrySet()) {
                Integer itemID = entry.getKey();
                Integer itemQuantity = entry.getValue();
                Integer inventoryQuantity = inventoryRepository.findById(itemID).block().getQuantity();
                if (inventoryQuantity < itemQuantity) {
                    Logger.getLogger("EventLog").info("production cannot get the required items from inventory");
                    // TODO cancel production -> return all the received items to inventory, update the status of sales order to new
                    return;
                }
                inventoryRepository.addToQuantity(itemID, -itemQuantity).subscribe();
                int used_quantity = itemQuantity;
                if (plannedProduct.getUsedItems().containsKey(itemID)) {
                    used_quantity += plannedProduct.getUsedItems().get(itemID);
                }
                plannedProduct.getUsedItems().put(itemID, used_quantity);

            }
            plannedProductsRepository.updateUsedItems(productionID, plannedProduct.getUsedItems()).block();
            ProductionEvent productionEvent = new ProductionEvent(productionID);
            Logger.getLogger("EventLog").info("production " + productionID + " is unblocked and scheduled");

            //TODO set real time
            Runnable exampleRunnable = () -> applicationEventPublisher.publishEvent(productionEvent);
            ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
            scheduler = new ConcurrentTaskScheduler(localExecutor);
            scheduler.schedule(exampleRunnable, new Date(System.currentTimeMillis() + 120000));
            //scheduler.schedule(exampleRunnable, Date.from(plannedProduct.getProductionDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            Logger.getLogger("EventLog").info("production " + productionID + " cannot be scheduled");
        }
    }
}

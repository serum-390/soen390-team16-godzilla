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

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

    @EventListener
    public void handleInventoryEvent(InventoryEvent event){
        Logger.getLogger("EventLog").info("production " + event.getProductionID() +" is unblocked and scheduled");
        Integer productionID = event.getProductionID();
        Integer purchaseID = event.getPurchaseOrderID();
        //TODO update the production status
        plannedProductsRepository.update(productionID,"processing").subscribe();
        PlannedProduct plannedProduct = plannedProductsRepository.findById(productionID).block();
        //TODO take the items from inventory
        Order purchaseOrder = ordersRepository.findById(purchaseID).block();

        for(Map.Entry<Integer,Integer> entry : purchaseOrder.getItems().entrySet()){
                Integer itemID = entry.getKey();
                Integer itemQuantity = entry.getValue();
                inventoryRepository.updateAdd(itemID,-itemQuantity).subscribe();
        }
        ProductionEvent productionEvent = new ProductionEvent(productionID);

        //TODO set timer
        Runnable exampleRunnable = () -> applicationEventPublisher.publishEvent(productionEvent);
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        scheduler.schedule(exampleRunnable, new Date(System.currentTimeMillis()+120000));
        //scheduler.schedule(exampleRunnable, Date.from(plannedProduct.getProductionDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}

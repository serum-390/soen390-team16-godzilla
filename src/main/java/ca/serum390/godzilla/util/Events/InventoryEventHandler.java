package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class InventoryEventHandler {

    private final PlannedProductsRepository plannedProductsRepository;
    private final OrdersRepository ordersRepository;
    private final InventoryRepository inventoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InventoryEventHandler(PlannedProductsRepository plannedProductsRepository, OrdersRepository ordersRepository, InventoryRepository inventoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plannedProductsRepository = plannedProductsRepository;
        this.ordersRepository = ordersRepository;
        this.inventoryRepository = inventoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void handleInventoryEvent(InventoryEvent event){
        Logger.getLogger("EventLog").info("order for production " + event.getProductionID() +"  has arrived in inventory");
        Integer productionID = event.getProductionID();
        Integer purchaseID = event.getPurchaseOrderID();
        //TODO update the production status
        plannedProductsRepository.update(productionID,"processing").subscribe();

        //TODO take the items from inventory
        Order purchaseOrder = ordersRepository.findById(purchaseID).block();

        for(Map.Entry<Integer,Integer> entry : purchaseOrder.getItems().entrySet()){
                Integer itemID = entry.getKey();
                Integer itemQuantity = entry.getValue();
                inventoryRepository.updateAdd(itemID,-itemQuantity).subscribe();
        }
        ProductionEvent productionEvent = new ProductionEvent(productionID);
        applicationEventPublisher.publishEvent(productionEvent);
    }
}

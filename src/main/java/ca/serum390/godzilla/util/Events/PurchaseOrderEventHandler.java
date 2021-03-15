package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.InventoryRepository;
import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class PurchaseOrderEventHandler {

    private final OrdersRepository ordersRepository;
    private final InventoryRepository inventoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PurchaseOrderEventHandler(OrdersRepository ordersRepository, InventoryRepository inventoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.ordersRepository = ordersRepository;
        this.inventoryRepository = inventoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //TODO possible issue : before the product handler gets the item from inventory, another order gets it
    // sol1 : directly handle the purchase event in the inventory handler
    // sol2 : handle this error in the inventory event handler by checking the availability of items
    @EventListener
    public void handlePurchaseOrderEvent(PurchaseOrderEvent event) {
        Integer orderID = event.getOrderID();
        Order purchaseOrder = ordersRepository.findById(orderID).block();
        if (purchaseOrder != null) {
            for (Map.Entry<Integer, Integer> orderEntry : purchaseOrder.getItems().entrySet()) {
                Integer itemID = orderEntry.getKey();
                Integer itemQuantity = orderEntry.getValue();
                inventoryRepository.addToQuantity(itemID, itemQuantity).subscribe();
            }

            Logger.getLogger("EventLog").info("Purchase order " + orderID + " is received in the inventory");
            ordersRepository.updateStatus(purchaseOrder.getId(), Order.COMPLETED).subscribe();
            InventoryEvent inventoryEvent = new InventoryEvent(purchaseOrder.getProductionID(), purchaseOrder.getId());
            applicationEventPublisher.publishEvent(inventoryEvent);
        } else {
            Logger.getLogger("EventLog").info("Purchase order " + orderID + " is invalid");
        }
    }

}

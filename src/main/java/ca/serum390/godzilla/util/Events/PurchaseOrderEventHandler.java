package ca.serum390.godzilla.util.Events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderEventHandler {

    @EventListener
    public void handlePurchaseOrderEvent(PurchaseOrderEvent event) {
        Integer orderID = event.getOrderID();
        System.out.println("Order "+orderID+" is received in the inventory");
    }

}

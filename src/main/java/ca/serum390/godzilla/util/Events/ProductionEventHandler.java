package ca.serum390.godzilla.util.Events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductionEventHandler {

    @EventListener
    public void handleProductionEvent(ProductionEvent event) {
        Integer orderID = event.getProductionID();
        System.out.println("production "+orderID+" completed");
    }
}

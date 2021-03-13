package ca.serum390.godzilla.util.Events;

import org.springframework.stereotype.Component;

@Component
public class InventoryEventHandler {

    public void handleInventoryEvent(InventoryEvent event){
        System.out.println("order for production " + event.getProductionID() +"  has arrive in inventory");
        //TODO update the production status
    }
}

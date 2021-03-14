package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventHandler {

    private final PlannedProductsRepository plannedProductsRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InventoryEventHandler(PlannedProductsRepository plannedProductsRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plannedProductsRepository = plannedProductsRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void handleInventoryEvent(InventoryEvent event){
        System.out.println("order for production " + event.getProductionID() +"  has arrived in inventory");
        Integer productionID = event.getProductionID();
        plannedProductsRepository.update(productionID,"processing").subscribe();
        //TODO update the production status
        //TODO take the item from inventory
        ProductionEvent productionEvent = new ProductionEvent(productionID);
        applicationEventPublisher.publishEvent(productionEvent);
    }
}

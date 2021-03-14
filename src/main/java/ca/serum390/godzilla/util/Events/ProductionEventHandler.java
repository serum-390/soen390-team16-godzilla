package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductionEventHandler {

    private final PlannedProductsRepository plannedProductsRepository;
    private final OrdersRepository ordersRepository;

    public ProductionEventHandler(PlannedProductsRepository plannedProductsRepository, OrdersRepository ordersRepository) {
        this.plannedProductsRepository = plannedProductsRepository;
        this.ordersRepository = ordersRepository;
    }

    @EventListener
    public void handleProductionEvent(ProductionEvent event) {
        Integer productionID = event.getProductionID();
        System.out.println("production "+productionID+" completed");
        PlannedProduct plannedProduct = plannedProductsRepository.findById(productionID).block();
        plannedProductsRepository.update(productionID,"completed").subscribe();
        ordersRepository.update(plannedProduct.getOrderID(),"completed").subscribe();

    }
}

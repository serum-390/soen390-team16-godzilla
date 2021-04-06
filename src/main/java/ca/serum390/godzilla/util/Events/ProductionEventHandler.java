package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PlannedProductsRepository;
import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ProductionEventHandler {

    private final PlannedProductsRepository plannedProductsRepository;
    private final OrdersRepository ordersRepository;

    public ProductionEventHandler(PlannedProductsRepository plannedProductsRepository, OrdersRepository ordersRepository) {
        this.plannedProductsRepository = plannedProductsRepository;
        this.ordersRepository = ordersRepository;
    }

    // completes the production
    //sets the production status to complete and the sales order status to ready
    @EventListener
    public void handleProductionEvent(ProductionEvent event) {
        Integer productionID = event.getProductionID();
        PlannedProduct plannedProduct = plannedProductsRepository.findById(productionID).block();
        if (plannedProduct != null && plannedProduct.getStatus().equals(PlannedProduct.SCHEDULED)) {
            Logger.getLogger("ProductionLog").info("production " + productionID + " completed");
            plannedProductsRepository.updateStatus(productionID, PlannedProduct.COMPLETED).subscribe();
            ordersRepository.updateStatus(plannedProduct.getOrderID(), Order.READY).subscribe();
        } else {
            Logger.getLogger("ProductionLog").info("production " + productionID + " cannot be completed");
        }
    }
}

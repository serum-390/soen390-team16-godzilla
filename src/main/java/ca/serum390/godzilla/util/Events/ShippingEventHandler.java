package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ShippingEventHandler {

    private final OrdersRepository ordersRepository;
    private final ShippingRepository shippingRepository;

    public ShippingEventHandler(OrdersRepository ordersRepository, ShippingRepository shippingRepository) {
        this.ordersRepository = ordersRepository;
        this.shippingRepository = shippingRepository;
    }

    @EventListener
    public void handleShippingEvent(ShippingEvent event) {
        int shippingID = event.getShippingID();
        shippingRepository.findById(shippingID).subscribe(shipping -> {
            if (shipping.getStatus().equals(Shipping.SCHEDULED)) {
                int salesOrderID = shipping.getOrderID();
                shippingRepository.updateStatus(shippingID, Shipping.SHIPPED).subscribe();
                ordersRepository.updateStatus(salesOrderID, Order.SHIPPED).subscribe();
                Logger.getLogger("ShippingLog").info("Shipping " + shippingID + " completed");
            } else {
                Logger.getLogger("ShippingLog").info(" unable to complete Shipping " + shippingID);
            }
        });
    }
}

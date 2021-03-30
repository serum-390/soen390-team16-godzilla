package ca.serum390.godzilla.util.Events;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
        int salesOrderID = event.getSalesOrderID();
        shippingRepository.updateStatus(shippingID, Shipping.SHIPPED);
        ordersRepository.updateStatus(salesOrderID, Order.SHIPPED);
    }
}

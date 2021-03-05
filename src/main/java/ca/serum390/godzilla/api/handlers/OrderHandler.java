package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.sales.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class OrderHandler {

    private final OrdersRepository ordersRepository;

    public OrderHandler(OrdersRepository salesOrder) {
        this.ordersRepository = salesOrder;
    }

    // Get All the sales orders
    public Mono<ServerResponse> all(ServerRequest request, String type) {
        return ok().body(ordersRepository.findAll(type), Order.class);
    }

    // Create a sales order
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Order.class).flatMap(ordersRepository::save).flatMap(id -> noContent().build());
    }

    // Get a sales order
    public Mono<ServerResponse> get(ServerRequest req, String type) {
        return ordersRepository.findById(Integer.parseInt(req.pathVariable("id")), type)
                .flatMap(salesOrder -> ok().body(Mono.just(salesOrder), Order.class))
                .switchIfEmpty(notFound().build());
    }

    // Delete a sales order
    public Mono<ServerResponse> delete(ServerRequest req, String type) {
        return ordersRepository.deleteById(Integer.parseInt(req.pathVariable("id")), type).flatMap(deleted -> noContent().build());
    }

    // Update a sales order
    public Mono<ServerResponse> update(ServerRequest req, String type) {
        var existed = ordersRepository.findById(Integer.parseInt(req.pathVariable("id")), type);
        return Mono.zip(data -> {
            Order g = (Order) data[0];
            Order g2 = (Order) data[1];
            if (g2 != null) {
                g.setCreatedDate(g2.getCreatedDate());
                g.setDueDate(g2.getDueDate());
                g.setDeliveryLocation(g2.getDeliveryLocation());
                g.setOrderType(g2.getOrderType());
            }
            return g;
        }, existed, req.bodyToMono(Order.class)).cast(Order.class)
                .flatMap(salesOrder -> ordersRepository.update(
                        salesOrder.getCreatedDate(),
                        salesOrder.getDueDate(),
                        salesOrder.getDeliveryLocation(),
                        salesOrder.getOrderType(),
                        salesOrder.getId()))
                .flatMap(salesOrder -> noContent().build());
    }

}

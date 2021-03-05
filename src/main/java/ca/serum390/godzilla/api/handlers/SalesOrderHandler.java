package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.sales.Order;
import reactor.core.publisher.Mono;

@Component
public class SalesOrderHandler {

    private final OrdersRepository salesOrders;

    public SalesOrderHandler(OrdersRepository salesOrder) {
        this.salesOrders = salesOrder;
    }

    // Get All the sales orders
    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().body(salesOrders.findAll(), Order.class);
    }

    // Create a sales order
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Order.class).flatMap(salesOrders::save).flatMap(id -> noContent().build());
    }

    // Get a sales order
    public Mono<ServerResponse> get(ServerRequest req) {
        return salesOrders.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesOrder -> ok().body(Mono.just(salesOrder), Order.class))
                .switchIfEmpty(notFound().build());
    }

    // Delete a sales order
    public Mono<ServerResponse> delete(ServerRequest req) {
        return salesOrders.deleteById(Integer.parseInt(req.pathVariable("id"))).flatMap(deleted -> noContent().build());
    }

    // Update a sales order
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = salesOrders.findById(Integer.parseInt(req.pathVariable("id")));
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
                .flatMap(salesOrder -> salesOrders.update(
                    salesOrder.getCreatedDate(),
                    salesOrder.getDueDate(),
                    salesOrder.getDeliveryLocation(),
                    salesOrder.getOrderType(),
                    salesOrder.getId()))
                .flatMap(salesOrder -> noContent().build());
    }

}

package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class OrderHandler {

    private final OrdersRepository ordersRepository;

    public OrderHandler(OrdersRepository salesOrder) {
        this.ordersRepository = salesOrder;
    }

    // Get All the orders
    public Mono<ServerResponse> all(ServerRequest request) {
        Optional<String> type = request.queryParam("type");
        Optional<String> status = request.queryParam("status");
        if (type.isPresent() && status.isPresent()) {
            return ok().body(ordersRepository.findAllBy(type.get(), status.get()), Order.class);
        } else if (type.isPresent()) {
            return ok().body(ordersRepository.findAllByType(type.get()), Order.class);
        } else if (status.isPresent()) {
            return ok().body(ordersRepository.findAllByStatus(status.get()), Order.class);
        } else {
            return ok().body(ordersRepository.findAll(), Order.class);
        }
    }

    // Create an order
    public Mono<ServerResponse> create(ServerRequest req) {
        System.out.println("HEGNJKRDFGJKDSJFLKDKFKLDGJK 23243432432");
        return req.bodyToMono(Order.class).flatMap(order -> ordersRepository.save(order.getCreatedDate(),
                order.getDueDate(), order.getDeliveryLocation(), order.getOrderType(), order.getStatus(), order.getItems()))
                .flatMap(id -> noContent().build());
    }

    // Get an order by id
    public Mono<ServerResponse> get(ServerRequest req) {
        return ordersRepository.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesOrder -> ok().body(Mono.just(salesOrder), Order.class))
                .switchIfEmpty(notFound().build());
    }

    // Delete an order
    public Mono<ServerResponse> delete(ServerRequest req) {
        return ordersRepository.deleteById(Integer.parseInt(req.pathVariable("id"))).flatMap(deleted -> noContent().build());
    }

    // Update an order
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = ordersRepository.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            Order g = (Order) data[0];
            Order g2 = (Order) data[1];
            if (g2 != null) {
                g.setCreatedDate(g2.getCreatedDate());
                g.setDueDate(g2.getDueDate());
                g.setDeliveryLocation(g2.getDeliveryLocation());
                g.setOrderType(g2.getOrderType());
                g.setItems(g2.getItems());
                g.setStatus(g2.getStatus());
            }
            return g;
        }, existed, req.bodyToMono(Order.class)).cast(Order.class)
                .flatMap(salesOrder -> ordersRepository.update(
                        salesOrder.getCreatedDate(),
                        salesOrder.getDueDate(),
                        salesOrder.getDeliveryLocation(),
                        salesOrder.getOrderType(),
                        salesOrder.getId(),
                        salesOrder.getStatus(),
                        salesOrder.getItems()))
                .flatMap(salesOrder -> noContent().build());
    }

}

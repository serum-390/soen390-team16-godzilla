package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import ca.serum390.godzilla.data.repositories.SalesOrderRepository;
import ca.serum390.godzilla.domain.sales.SalesOrder;
import reactor.core.publisher.Mono;

@Component
public class SalesHandler {

    private final SalesOrderRepository salesOrders;

    public SalesHandler(SalesOrderRepository salesOrder) {
        this.salesOrders = salesOrder;
    }

    // Get All the sales orders
    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().body(salesOrders.findAll(), SalesOrder.class);
    }

    // Create a sales order
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(SalesOrder.class).flatMap(salesOrders::save).flatMap(id -> noContent().build());
    }

    // Get a sales order
    public Mono<ServerResponse> get(ServerRequest req) {
        return salesOrders.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesOrder -> ok().body(Mono.just(salesOrder), SalesOrder.class))
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
            SalesOrder g = (SalesOrder) data[0];
            SalesOrder g2 = (SalesOrder) data[1];
            if (g2 != null) {
                g.setCreatedDate(g2.getCreatedDate());
                g.setDueDate(g2.getDueDate());
                g.setDeliveryLocation(g2.getDeliveryLocation());
                g.setOrderType(g2.getOrderType());
            }
            return g;
        }, existed, req.bodyToMono(SalesOrder.class)).cast(SalesOrder.class)
                .flatMap(SalesOrder -> salesOrders.update(SalesOrder.getCreatedDate(), SalesOrder.getDueDate(),
                        SalesOrder.getDeliveryLocation(), SalesOrder.getOrderType(), SalesOrder.getId()))
                .flatMap(SalesOrder -> noContent().build());
    }

}

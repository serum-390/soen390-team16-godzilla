package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void salesOrderHandler(SalesOrderRepository salesOrder) {
        this.salesOrders = salesOrder;
    }

    public Mono<ServerResponse> demoSales(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValue(buildDemoSalesList());
    }

    private static Map<Object, Object> buildDemoSalesList() {
        return map().with("message", "Success. Here are your sales orders").with("sales", Stream
                .iterate(1, i -> i <= 50, i -> i + 1)
                .map(val -> map().with("id", val).with("vendorName", "Vendor-" + val).with("vendorAddress", "????")
                        .with("vendorPhone", "123-456-7890").with("salesOrders", List.of()))
                .collect(Collectors.toList()));
    }
}

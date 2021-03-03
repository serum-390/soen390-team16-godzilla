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

import reactor.core.publisher.Mono;

@Component
public class ProductionHandler {
    private ProductionHandler() {}

    public Mono<ServerResponse> demoMaterials(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(buildDemoMaterialsList());
    }

    private static Map<Object, Object> buildDemoMaterialsList() {
        return map().with("message", "Success. Here are your materials list")
                    .with("materials", Stream
                        .iterate(1, i -> i <= 50, i -> i + 1)
                        .map(val -> map()
                            .with("id", val)
                            .with("material", "Material-" + val)
                            .with("mID", "????")
                            .with("status", "In Stock/Backorder")
                            .with("allMaterials", List.of()))
                        .collect(Collectors.toList()));
    }

    public Mono<ServerResponse> demoProducts(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(buildDemoProductsList());
    }

    private static Map<Object, Object> buildDemoProductsList() {
        return map().with("message", "Success. Here are your products orders")
                    .with("products", Stream
                        .iterate(1, i -> i <= 10, i -> i + 1)
                        .map(val -> map()
                            .with("id", val)
                            .with("product", "Product-" + val)
                            .with("requiredMaterials", "????")
                            .with("production", 0)
                            .with("allProducts", List.of()))
                        .collect(Collectors.toList()));
    }
}

package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableJsonMap.map;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import reactor.core.publisher.Mono;

@Component
public class ProductionHandler {
    private ProductionHandler() {}

    public Mono<ServerResponse> demoMaterials(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .body(buildDemoMaterialsList(), Map.class);
    }

    public Mono<ServerResponse> demoProducts(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .body(buildDemoProductsList(), Map.class);
    }

    private static Mono<Map<String, Object>> buildDemoProductsList() {
        return buildDemoObjectList(
                () -> "products",
                val -> map()
                    .with("id", val)
                    .with("product", "Product-" + val)
                    .with("requiredMaterials", "????")
                    .with("production", 0)
                    .with("allProducts", List.of()));
    }

    private static Mono<Map<String, Object>> buildDemoMaterialsList() {
        return buildDemoObjectList(
                () -> "materials",
                id -> map()
                    .with("id", id)
                    .with("material", "Material-" + id)
                    .with("mID", "????")
                    .with("status", "In Stock/Backorder")
                    .with("allMaterials", List.of()));
    }

    private static Mono<Map<String, Object>> buildDemoObjectList(
            Supplier<String> itemName,
            IntFunction<Object> objectSupplier) {
        return Mono.fromCallable(() -> map(String.class, Object.class)
                .with("message", "Success. Here is your " + itemName.get() +" list")
                .with(itemName.get(), Stream
                    .iterate(1, i -> i <= 50, i -> i + 1)
                    .map(objectSupplier::apply)
                    .collect(toList())));
    }
}

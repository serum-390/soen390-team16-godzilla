package ca.serum390.godzilla.handlers;

import static ca.serum390.godzilla.helper.BuildableMap.map;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class InventoryHandler {

    private InventoryHandler() {}

    public static Mono<ServerResponse> demoInventory(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .bodyValue(buildDemoInventory());
    }

    private static Map<Object, Object> buildDemoInventory() {
        return map().with("message", "Success. Here is your inventory")
                    .with("inventory", List.of(
                        map().with("name", "Bike Wheel")
                             .with("type", "raw-material")
                             .with("image_url", "/resources/images/wheel.jpeg")
                             .with("description", "A bicycle wheel."),
                        map().with("name", "Drive Train")
                             .with("type", "raw-material")
                             .with("image_url", "/resources/images/drive-train.jpeg")
                             .with("description", "A gear system and bike chain."),
                        map().with("name", "Full Bike")
                             .with("type", "finished-product")
                             .with("image_url", "/resources/images/full-bike.jpeg")
                             .with("description", "A finished bicycle.")
                    ));
    }
}

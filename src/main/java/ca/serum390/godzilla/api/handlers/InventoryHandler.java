package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class InventoryHandler {

    private InventoryHandler() {}

    public Mono<ServerResponse> demoInventory(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(buildDemoInventory());
    }

    private static Map<Object, Object> buildDemoInventory() {
        final String NAME = "name";
        final String TYPE = "type";
        final String IMAGE_URL = "image_url";
        final String DESCRIPTION = "description";

        return map().with("message", "Success. Here is your inventory")
                    .with("inventory", List.of(
                        map().with(NAME, "Bike Wheel")
                             .with(TYPE, "raw-material")
                             .with(IMAGE_URL, "/resources/images/wheel.jpeg")
                             .with(DESCRIPTION, "A bicycle wheel."),
                        map().with(NAME, "Drive Train")
                             .with(TYPE, "raw-material")
                             .with(IMAGE_URL, "/resources/images/drive-train.jpeg")
                             .with(DESCRIPTION, "A gear system and bike chain."),
                        map().with(NAME, "Full Bike")
                             .with(TYPE, "finished-product")
                             .with(IMAGE_URL, "/resources/images/full-bike.jpeg")
                             .with(DESCRIPTION, "A finished bicycle.")
                    ));
    }
}

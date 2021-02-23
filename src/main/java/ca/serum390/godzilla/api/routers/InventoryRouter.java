package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.InventoryHandler;

@Configuration
public class InventoryRouter {

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute(InventoryHandler inventoryHandler) {
        return RouterFunctions.route()
                .path("/inventory/", builder -> builder
                    .GET("/", inventoryHandler::getBy)
                    .DELETE("/{id}", inventoryHandler::deleteById))
                .build();
    }
}

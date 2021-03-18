package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.PlannedProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PlannedProductRouter {
    @Bean
    public RouterFunction<ServerResponse> plannedProductRoute(PlannedProductHandler plannedProductHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/planning/", builder -> builder
                        .GET("/", plannedProductHandler::getBy)
                        .GET(ID, plannedProductHandler::getByID)
                        .POST("/", plannedProductHandler::create)
                        .PUT(ID, plannedProductHandler::update)
                        .DELETE(ID, plannedProductHandler::delete))
                .build();
    }
}

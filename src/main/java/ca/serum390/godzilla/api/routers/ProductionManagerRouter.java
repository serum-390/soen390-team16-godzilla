package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.PlannedProductHandler;
import ca.serum390.godzilla.api.handlers.ProductionManagerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductionManagerRouter {
    @Bean
    public RouterFunction<ServerResponse> productionManagerRoute(ProductionManagerHandler productionManagerHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/production-manager/", builder -> builder
                        .POST("/validate", productionManagerHandler::validateProduction)
                        .POST("/cancel" + ID, productionManagerHandler::cancelProduction))
                .build();
    }
}
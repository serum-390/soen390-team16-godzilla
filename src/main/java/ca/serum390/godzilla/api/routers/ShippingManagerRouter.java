package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.ShippingManagerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ShippingManagerRouter {
    @Bean
    public RouterFunction<ServerResponse> shippingManagerRoute(ShippingManagerHandler shippingManagerHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/shipping-manager/", builder -> builder
                        .POST("/validate", shippingManagerHandler::validateShipping)
                        .POST("/cancel" + ID, shippingManagerHandler::cancelShipping))
                .build();
    }
}
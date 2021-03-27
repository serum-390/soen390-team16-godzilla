package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.ShippingHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@AllArgsConstructor
public class ShippingRouter {

    ShippingHandler shippingHandler;

    @Bean
    public RouterFunction<ServerResponse> shippingRoute() {
        return RouterFunctions.route()
                .path("/shipping/", builder -> builder
                        .GET("/", shippingHandler::get)
                        .DELETE("/{id}", shippingHandler::delete)
                        .PUT("/{id}", shippingHandler::update)
                        .POST("/", shippingHandler::create))
                .build();
    }
}
package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.OrderHandler;

@Configuration
public class OrderRouter {
    @Bean
    public RouterFunction<ServerResponse> orderRoute(OrderHandler orderHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/orders/", builder -> builder
                    .GET("/", orderHandler::all)
                    .POST("/", orderHandler::create)
                    .GET(ID, orderHandler::get)
                    .PUT(ID, orderHandler::update)
                    .DELETE(ID, orderHandler::delete))
                .build();
    }
}

package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrderRouter {
    @Bean
    public RouterFunction<ServerResponse> orderRoute(OrderHandler salesHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/sales/", builder -> builder
                        .GET("/", req -> salesHandler.all(req, "sales"))
                        .POST("/", salesHandler::create)
                        .GET(ID, req -> salesHandler.get(req, "sales"))
                        .PUT(ID, req -> salesHandler.update(req, "sales"))
                        .DELETE(ID, req -> salesHandler.delete(req, "sales")))
                .path("/purchase/", builder -> builder
                        .GET("/", req -> salesHandler.all(req, "purchase"))
                        .POST("/", salesHandler::create)
                        .GET(ID, req -> salesHandler.get(req, "purchase"))
                        .PUT(ID, req -> salesHandler.update(req, "purchase"))
                        .DELETE(ID, req -> salesHandler.delete(req, "purchase")))
                .build();
    }
}

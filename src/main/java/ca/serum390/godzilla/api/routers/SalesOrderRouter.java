package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.SalesHandler;

@Configuration
public class SalesOrderRouter {
    @Bean
    public RouterFunction<ServerResponse> salesOrderRoute(SalesHandler salesHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/sales/", builder -> builder.GET("/", salesHandler::all).POST("/", salesHandler::create)
                        .GET(ID, salesHandler::get).PUT(ID, salesHandler::update).DELETE(ID, salesHandler::delete))
                .build();
    }
}

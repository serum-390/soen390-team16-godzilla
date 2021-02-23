package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.SalesContactHandler;

@Configuration
public class SalesContactRouter {
    @Bean
    public RouterFunction<ServerResponse> salesContactRoute(SalesContactHandler salesContactHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/salescontact/",
                        builder -> builder.GET("/", salesContactHandler::all).POST("/", salesContactHandler::create)
                                .GET(ID, salesContactHandler::get).PUT(ID, salesContactHandler::update)
                                .DELETE(ID, salesContactHandler::delete))
                .build();
    }
}
package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.PackagedProductHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class PackagedProductRouter {

    PackagedProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse> packagedRoute() {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/packagedproduct/",builder -> builder
                        .GET("/", productHandler::all)
                        .POST("/", productHandler::create)
                        .GET(ID, productHandler::get)
                        .PUT(ID, productHandler::update)
                        .DELETE(ID, productHandler::delete))
                .build();
    }
}
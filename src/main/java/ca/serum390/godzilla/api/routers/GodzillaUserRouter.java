package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.GodzillaUserHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GodzillaUserRouter{

    GodzillaUserHandler godzillaUserHandler;

    @Bean
    public RouterFunction<ServerResponse> godzillaUserRoute() {
        return RouterFunctions.route()
                .path("/signup/", builder -> builder
                    //.GET("/", inventoryHandler::getBy)
                    //.DELETE("/{id}", inventoryHandler::deleteById)
                    //.PUT("/{id}", inventoryHandler::update)
                    .POST("/", GodzillaUserHandler::create))
                .build();
    }
}
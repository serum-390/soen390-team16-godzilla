package ca.serum390.godzilla.api.routers;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.BomHandler;

@Configuration
public class BomRouter {

    @Bean
    public RouterFunction<ServerResponse> bomRoute(BomHandler bomHandler){
        return route()
                .path("/bom/", builder -> builder
                    .GET("/", bomHandler::getAll)
                    .GET("/{itemName}", bomHandler::findAllByID)
                    .POST("/", bomHandler::insert))
                .build();
    }
}

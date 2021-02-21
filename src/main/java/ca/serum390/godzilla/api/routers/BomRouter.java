package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.BomHandler;

@Configuration
public class BomRouter {
    
    @Bean
    public RouterFunction<ServerResponse> bomRoute(BomHandler bomHandler){
        final String ID = "/{item_name}";
        return RouterFunctions.route()
                .path("/bom/", builder -> builder
                    .GET("/", bomHandler::getAll)
                    .GET(ID, bomHandler::findAllByID)
                    //.DELETE(ID, bomHandler::deleteById)
                    .POST("/", bomHandler::insert)
                ).build();    
    }
}

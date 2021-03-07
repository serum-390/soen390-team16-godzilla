package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.VenderContactHandler;

@Configuration
public class VenderContactRouter {
    @Bean
    public RouterFunction<ServerResponse> venderContactRoute(VenderContactHandler venderContactHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                    .path("/vendercontact/",
                        builder -> builder.GET("/", venderContactHandler::all)
                        .POST("/", venderContactHandler::create)
                                .GET(ID, venderContactHandler::getVender)
                                .PUT(ID, venderContactHandler::updateVender)
                                .DELETE(ID, venderContactHandler::delete))
                .build();
    }
}

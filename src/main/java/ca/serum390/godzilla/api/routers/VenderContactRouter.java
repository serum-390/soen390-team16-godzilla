package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.ContactHandler;

@Configuration
public class VenderContactRouter {
    @Bean
    public RouterFunction<ServerResponse> venderContactRouter(ContactHandler contactHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                    .path("/vendercontact/",
                        builder -> builder.GET("/", contactHandler::all)
                        .POST("/", contactHandler::createVendor)
                                .GET(ID, contactHandler::getVendor)
                                .PUT(ID, contactHandler::updateVendor)
                                .DELETE(ID, contactHandler::delete))
                .build();
    }
}

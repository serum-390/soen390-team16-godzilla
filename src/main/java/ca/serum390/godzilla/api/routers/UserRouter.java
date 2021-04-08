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
public class UserRouter {

    RouterFunction<ServerResponse> permissionsRoute;
    GodzillaUserHandler godzillaUserHandler;

    @Bean
    public RouterFunction<ServerResponse> usersRoute() {
        return RouterFunctions.route()
                .path("/users", builder -> builder
                    .POST("/signup", godzillaUserHandler::create)
                    .add(permissionsRoute))
                .build();
    }
}

package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.PermissionsHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class PermissionsRouter {

    PermissionsHandler permissionsHandler;

    @Bean
    public RouterFunction<ServerResponse> permissionsRoute() {
        return RouterFunctions.route()
                .path("/permissions", builder -> builder
                    .GET("/", permissionsHandler::getPermissions)
                    .PATCH("/", permissionsHandler::changePermission))
                .build();
    }
}

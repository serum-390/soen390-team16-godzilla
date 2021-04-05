package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class UserRouter {

    RouterFunction<ServerResponse> permissionsRoute;

    @Bean
    public RouterFunction<ServerResponse> usersRoute() {
        return RouterFunctions.route()
                .path("/users", builder -> builder
                    .add(permissionsRoute))
                .build();
    }
}

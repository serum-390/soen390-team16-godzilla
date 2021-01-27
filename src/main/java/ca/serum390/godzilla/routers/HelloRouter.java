package ca.serum390.godzilla.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> helloRoute() {
        return RouterFunctions.route(
            RequestPredicates.GET("/hello/"),
            HelloRouter::helloWorld
        );
    }

    private static Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_PLAIN)
                             .body(BodyInserters.fromValue("Hello World!"));
    }
}

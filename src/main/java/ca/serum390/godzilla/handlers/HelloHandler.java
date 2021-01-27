package ca.serum390.godzilla.handlers;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class HelloHandler {

    private HelloHandler() {}

    public static Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_PLAIN)
                             .body(BodyInserters.fromValue("Hello World!"));
    }
}

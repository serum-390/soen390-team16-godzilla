/*package ca.serum390.godzilla.util.experimental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class HelloHandler {

    private HelloHandler() {}

    @Autowired
    //private ComponentBoi myComponentBoi;

    public Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_PLAIN)
                             .bodyValue(myComponentBoi.lookWhatYouDidToMyBoi());
    }
}
*/
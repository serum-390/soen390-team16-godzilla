package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.user.GodzillaUser;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class GodzillaUserHandler {

    /**
     * {@link InventoryRepository}
     */
    private final GodzillaUserRepository new_users;

    public GodzillaUserHandler (GodzillaUserRepository new_user) {
        this.new_users = new_user;
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(GodzillaUser.class)
                .flatMap(user -> new_users.save(user.getUsername(), user.getPassword()))
                .flatMap(id -> noContent().build());
    }

}
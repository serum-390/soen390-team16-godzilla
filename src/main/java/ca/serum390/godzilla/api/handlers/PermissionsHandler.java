package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableJsonMap.map;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.GodzillaUser;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PermissionsHandler {

    GodzillaUserRepository repo;

    /**
     * Update the permissions of a user
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> changePermission(ServerRequest request) {
        Optional<String> id = request.queryParam("id");
        Optional<String> name = request.queryParam("name");

        var defaultResponse = notFound().build();

        if (id.isPresent()) {
            return defaultResponse;
        } else if (name.isPresent()) {
            return defaultResponse;
        } else {
            return defaultResponse;
        }
    }

    /**
     * Query the permissions for a user
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getPermissions(ServerRequest request) {
        Optional<Mono<GodzillaUser>> byId;
        Supplier<Optional<Mono<GodzillaUser>>> byName;

        byId = request
                .queryParam("id")
                .flatMap(this::toInteger)
                .flatMap(i -> i < 0 ? Optional.empty() : Optional.of(i))
                .map(repo::findById);

        byName = () -> request
                .queryParam("name")
                .map(repo::findByUsername);

        return byId
                .or(byName)
                .map(this::convertUserToServerResponse)
                .orElseGet(() -> notFound().build());
    }

    private Mono<ServerResponse> convertUserToServerResponse(Mono<GodzillaUser> user) {
        return user.flatMap(this::userToPerms)
                .switchIfEmpty(Mono.defer(() -> notFound().build()));
    }

    private Mono<ServerResponse> userToPerms(GodzillaUser user) {
        return ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(map()
                    .with("isAdmin", false)
                    .with("roles", user
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::toString)
                        .collect(toList())));
    }

    private Optional<Integer> toInteger(String id) {
        try {
            return Optional.of(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
 }

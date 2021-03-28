package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.api.handlers.exceptions.CreateUserButAlreadyExistsException.forUser;
import static ca.serum390.godzilla.util.BuildableJsonMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.unprocessableEntity;
import static reactor.core.publisher.Mono.fromCallable;

import java.util.Map;

import org.springframework.boot.devtools.remote.server.Handler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import ca.serum390.godzilla.api.handlers.exceptions.CreateUserWithoutUsernameOrPasswordException;
import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.GodzillaUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;


@Log4j2
@Component
@RequiredArgsConstructor
public class GodzillaUserHandler {
    public static final String DEFAULT_AUTHORITIES = "ROLE_USER";
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";
    public static final String EMAIL_FIELD = "email";
    public static final String EMAIL_VALIDATOR = "^(.+)@(.+)$";
    public static final int MIN_USERNAME_LENGTH = 2;

    private final PasswordEncoder passwordEncoder;
    private final GodzillaUserRepository userRepository;

    /**
     * Create a new user with default authorities
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> create(ServerRequest req){
        return req.formData()
                .handle(CreateUserWithoutUsernameOrPasswordException::handle)
                .handle(this::handleUserNameTooShort)
                .flatMap(this::errorIfUserAlreadyExists)
                .flatMap(this::saveUser)
                .flatMap(user -> noContent().build())
                .onErrorResume(err -> unprocessableEntity().body(
                    fromCallable(() -> map("error", err.getMessage())), Map.class));
    }

    private Mono<Object> errorIfUserAlreadyExists(MultiValueMap<String, String> data) {
        return userRepository
                .findByUsername(data.getFirst(USERNAME_FIELD))
                .flatMap(u -> Mono.error(forUser(u.getUsername())))
                .switchIfEmpty(Mono.just(data));
    }

    private void handleUserNameTooShort(
            MultiValueMap<String, String> data, 
            SynchronousSink<MultiValueMap<String, String>> sink){
        var username = data.getFirst(USERNAME_FIELD);
        if(username.length() >= MIN_USERNAME_LENGTH) {
            sink.next(data);
        } else {
            sink.error(new RuntimeException("username '"+ username + "' to short, must be at least "+ MIN_USERNAME_LENGTH + " characters long." ));
        }
    }



    @SuppressWarnings("unchecked")
    private Mono<GodzillaUser> saveUser(Object data) {
        var map = (MultiValueMap<String, String>) data;
        String username = map.getFirst(USERNAME_FIELD);
        String password = passwordEncoder.encode(map.getFirst(PASSWORD_FIELD));
        String email = map.getFirst(EMAIL_FIELD);
        log.info("Creating new user: " + username);
        return userRepository.save(username, password, DEFAULT_AUTHORITIES,email);
    }
}

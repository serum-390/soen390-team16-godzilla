package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;



@Component
@RequiredArgsConstructor
@Log4j2
public class GodzillaUserHandler {
    public static final String DEFAULT_AUTHORITIES = "ROLE_USER";

    private final GodzillaUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    
    // import static 
    public Mono<ServerResponse> create(ServerRequest req){
        return req.formData().flatMap(data -> {
            String username = data.getFirst("username");
            log.debug(data);
            log.debug(data);
            log.debug(data);
            log.debug(data);
            log.debug(data);
            log.debug(data);
            log.debug(data);
            String password = passwordEncoder.encode(data.getFirst("password"));
            
            
            return userRepository.save(username,password,DEFAULT_AUTHORITIES);
        }).flatMap(user -> noContent().build());
        
        
        

        /*
        return req.bodyToMono(GodzillaUser.class)
                .flatMap(user -> new_user.save(user.getUsername(),user.getPassword(),user.getAuthorities()))
                .flatMap(id -> noContent().build());*/
    } 
    
}

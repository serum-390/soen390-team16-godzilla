package ca.serum390.godzilla.data.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ca.serum390.godzilla.domain.GodzillaUser;
import reactor.core.publisher.Mono;

public interface GodzillaUserRepository extends ReactiveCrudRepository<GodzillaUser, Integer> {


    
    Mono<GodzillaUser> findByUsername(String username);

}

package ca.serum390.godzilla.data.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import ca.serum390.godzilla.domain.user.GodzillaUser;

@Repository
public interface GodzillaUserRepository extends ReactiveCrudRepository<GodzillaUser, Integer> {

    Mono<GodzillaUser> findByUsername(String username);
}

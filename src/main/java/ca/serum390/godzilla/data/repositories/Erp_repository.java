package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.erp_user;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface Erp_repository  extends ReactiveCrudRepository<erp_user,Integer> {
    Mono<erp_user> findByUsername(String username);

}


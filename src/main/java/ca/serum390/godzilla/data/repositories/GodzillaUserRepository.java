package ca.serum390.godzilla.data.repositories;



import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.GodzillaUser;
import reactor.core.publisher.Mono;
@Repository
public interface GodzillaUserRepository extends ReactiveCrudRepository<GodzillaUser, Integer> {
    Mono<GodzillaUser> findByUsername(String username);
    
    @Query("INSERT INTO erp_user(username, password, authorities) values ($1,$2,$3) returning *")
    Mono<GodzillaUser> save(String username,String password, String authorities);

}

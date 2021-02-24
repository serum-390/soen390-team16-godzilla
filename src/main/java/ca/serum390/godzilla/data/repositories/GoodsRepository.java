package ca.serum390.godzilla.data.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.manufacturing.Good;
import reactor.core.publisher.Mono;

@Repository
public interface GoodsRepository extends ReactiveCrudRepository<Good, UUID> {

    /**
     * This is how you do a custom query on the repository.
     */
    @Modifying
    @Query("UPDATE goods SET name = $1, description = $2 WHERE id = $3")
    Mono<Integer> update(String name, String description, UUID id);
}

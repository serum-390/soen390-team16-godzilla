package ca.serum390.godzilla.data.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ca.serum390.godzilla.domain.billOfMaterial.BomEntry;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BomRepository extends ReactiveCrudRepository <BomEntry, Integer>{
    /**
     * Search by item_name
     */
    //@Modifying
    //@Query("SELECT * FROM bill_of_material WHERE item_name = $1")
	//Mono<ServerResponse> findAllById(int parseInt);
    
}

package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ca.serum390.godzilla.domain.vender.VenderContact;
import reactor.core.publisher.Mono;

public interface VenderContactRepository extends ReactiveCrudRepository<VenderContact, Integer>{
    /**
     * Query for vendor contact
     */

    //Find all customer
    @Modifying
    @Query("SELECT * FROM contact WHERE contact_type = 'vender'")
    Mono<Integer> findAllVender();

    //Find by id where contact_type = customer
    @Modifying
    @Query("SELECT * FROM contact WHERE ID = $1 AND contact_type = 'vender'")
    Mono<Integer> findByIdVender(int id);    
    //Update sales contact where contact_type = vendor
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE='vender'  WHERE ID = $5")
    Mono<Integer> updateVender(
            String companyName,
            String contactName,
            String address,
            String contact,
            String string, Integer id);
}


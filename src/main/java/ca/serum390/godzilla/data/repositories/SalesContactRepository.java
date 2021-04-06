package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ca.serum390.godzilla.domain.orders.SalesContact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesContactRepository extends ReactiveCrudRepository<SalesContact, Integer> {
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE= 'customer'  WHERE ID = $5")
    Mono<Integer> update(
            String companyName,
            String contactName,
            String address,
            String contact,
            Integer id);
    
    @Query("SELECT * FROM contact WHERE contact_type = 'customer'")
    Flux<SalesContact> findAllCustomer();

    /**
     * Find by id where contact_type = customer
     */
    @Query("SELECT * FROM contact WHERE ID = $1 AND contact_type = 'customer'")
    Mono<SalesContact> findByIdCustomer(Integer id);    
        
    /**
     * Find customer by name
     */
    @Query("SELECT * FROM contact WHERE CONTACT_NAME = $1 AND contact_type = 'customer'")
    Flux<SalesContact> findByNameCustomer(String name);      
}
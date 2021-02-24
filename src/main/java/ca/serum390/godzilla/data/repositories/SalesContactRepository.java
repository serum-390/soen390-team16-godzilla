package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ca.serum390.godzilla.domain.sales.SalesContact;
import reactor.core.publisher.Mono;

public interface SalesContactRepository extends ReactiveCrudRepository<SalesContact, Integer> {
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE=$5  WHERE ID = $6")
    Mono<Integer> update(
            String companyName,
            String contactName,
            String address,
            String contact,
            String contactType,
            Integer id);
}
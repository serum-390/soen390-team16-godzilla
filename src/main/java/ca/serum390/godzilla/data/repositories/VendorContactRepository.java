package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.vendor.VendorContact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VendorContactRepository extends ReactiveCrudRepository<VendorContact, Integer> {

    /**
     * Get all vendor
     */
    @Query("SELECT * FROM contact WHERE contact_type = 'vendor'")
    Flux<VendorContact> findAllVendor();

    /**
     * Find by id where contact_type = customer
     */
    @Query("SELECT * FROM contact WHERE ID = $1 AND contact_type = 'vendor'")
    Mono<VendorContact> findByIdVendor(int id);    

    /**
     * Update sales contact where contact_type = vendor
     */
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE='vendor'  WHERE ID = $5")
    Mono<Integer> updateVendor(
            String companyName,
            String contactName,
            String address,
            String contact, Integer id);
}

package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ca.serum390.godzilla.domain.contact.Contact;
import reactor.core.publisher.Mono;

public interface ContactRepository extends ReactiveCrudRepository<Contact, Integer>{

    /**
     * Query for sales contact
     */

    //Find all customer
    @Modifying
    @Query("SELECT * FROM contact WHERE contact_type = 'customer'")
    Mono<Integer> findAllCustomer();

    //Find by id where contact_type = customer
    @Modifying
    @Query("SELECT * FROM contact WHERE ID = $1 AND contact_type = 'customer'")
    Mono<Integer> findByIdCustomer(int id);

    //Update sales contact where contact_type = customer
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE='customer'  WHERE ID = $5")
    Mono<Integer> updateSales(
            String companyName,
            String contactName,
            String address,
            String contact,
            String contact_type,
            Integer id);

    /**
     * Query for vendor contact
     */

    //Find all customer
    @Modifying
    @Query("SELECT * FROM contact WHERE contact_type = 'vendor'")
    Mono<Integer> findAllVendor();

    //Find by id where contact_type = customer
    @Modifying
    @Query("SELECT * FROM contact WHERE ID = $1 AND contact_type = 'vendor'")
    Mono<Integer> findByIdVendor(int id);    
    //Update sales contact where contact_type = vendor
    @Modifying
    @Query("UPDATE contact SET COMPANY_NAME = $1, CONTACT_NAME = $2, ADDRESS= $3, CONTACT=$4, CONTACT_TYPE='vendor'  WHERE ID = $5")
    Mono<Integer> updateVendor(
            String companyName,
            String contactName,
            String address,
            String contact,
            Integer id);
}


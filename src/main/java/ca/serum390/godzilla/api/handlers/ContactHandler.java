package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

import ca.serum390.godzilla.data.repositories.ContactRepository;
import ca.serum390.godzilla.domain.contact.Contact;
import reactor.core.publisher.Mono;

@Component
public class ContactHandler {

    private final ContactRepository contacts;

    public ContactHandler(ContactRepository contact) {
        this.contacts = contact;
    }
    
    /**
     * API for sales
     */

    //get all customer contact
    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(contacts.findAllCustomer(), Contact.class);
    }

    //create a customer contact
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Contact.class).flatMap(contacts::save).flatMap(id -> noContent().build());
    }

    //get a customer contact
    public Mono<ServerResponse> getCustomer(ServerRequest req) {
        return contacts.findByIdCustomer(Integer.parseInt(req.pathVariable("id")))
        .flatMap(contact -> ok().body(Mono.just(contact), Contact.class))
        .switchIfEmpty(notFound().build());
    }

    // Delete a sales contact
    public Mono<ServerResponse> delete(ServerRequest req) {
        return contacts.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    } 
    
    //Update a sales contact
    public Mono<ServerResponse> updateSales(ServerRequest req) {
        var existed = contacts.findByIdCustomer(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            Contact g = (Contact) data[0];
            Contact g2 = (Contact) data[1];
            if (g2 != null) {
                g.setCompanyName(g2.getCompanyName());
                g.setContactName(g2.getContactName());
                g.setAddress(g2.getAddress());
                g.setContact(g2.getContact());
                g.setContactType(g2.getContactType());
            }
            return g;
        }, existed, req.bodyToMono(Contact.class)).cast(Contact.class)
                .flatMap(contact -> contacts.updateSales(contact.getCompanyName(),
                        contact.getContactName(), contact.getAddress(), contact.getContact(),
                        contact.getContactType(), contact.getId()))
                .flatMap(rows -> noContent().build());
    }    

    /**
     * API for vendor contact
     */
        //get all vendor contact
        public Mono<ServerResponse> allVendor(ServerRequest req) {
            return ok().body(contacts.findAllCustomer(), Contact.class);
        }
    
        //create a vendor contact
        public Mono<ServerResponse> createVendor(ServerRequest req) {
            return req.bodyToMono(Contact.class).flatMap(contacts::save).flatMap(id -> noContent().build());
        }
    
        //get a vendor contact
        public Mono<ServerResponse> getVendor(ServerRequest req) {
            return contacts.findByIdCustomer(Integer.parseInt(req.pathVariable("id")))
            .flatMap(contact -> ok().body(Mono.just(contact), Contact.class))
            .switchIfEmpty(notFound().build());
        }
    
        // Delete a vendor contact
        public Mono<ServerResponse> deleteVendor(ServerRequest req) {
            return contacts.deleteById(Integer.parseInt(req.pathVariable("id")))
                    .flatMap(deleted -> noContent().build());
        } 
        
        //Update a vendor contact
        public Mono<ServerResponse> updateVendor(ServerRequest req) {
            var existed = contacts.findByIdCustomer(Integer.parseInt(req.pathVariable("id")));
            return Mono.zip(data -> {
                Contact g = (Contact) data[0];
                Contact g2 = (Contact) data[1];
                if (g2 != null) {
                    g.setCompanyName(g2.getCompanyName());
                    g.setContactName(g2.getContactName());
                    g.setAddress(g2.getAddress());
                    g.setContact(g2.getContact());
                    g.setContactType(g2.getContactType());
                }
                return g;
            }, existed, req.bodyToMono(Contact.class)).cast(Contact.class)
                    .flatMap(contact -> contacts.updateVendor(contact.getCompanyName(),
                            contact.getContactName(), contact.getAddress(), contact.getContact(),
                            contact.getContactType(), contact.getId()))
                    .flatMap(rows -> noContent().build());
        }    
    
}

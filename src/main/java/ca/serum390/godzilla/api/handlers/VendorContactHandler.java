package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.VendorContactRepository;
import ca.serum390.godzilla.domain.vendor.VendorContact;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class VendorContactHandler {

    private final VendorContactRepository vendorContacts;

    public VendorContactHandler(VendorContactRepository vendorContact) {
        this.vendorContacts = vendorContact;
    }
    
    /**
     * get all vendor contact
     */
    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().contentType(APPLICATION_JSON).body(vendorContacts.findAllVendor(), VendorContact.class);
    }

    /**
     * create a vendor contact
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(VendorContact.class)
            .flatMap(vendorContacts::save)
            .flatMap(id -> noContent().build());
    }

    /**
     * get a vendor contact
     */
    public Mono<ServerResponse> getVendor(ServerRequest req) {
        return vendorContacts.findByIdVendor(Integer.parseInt(req.pathVariable("id")))
        .flatMap(contact -> ok().body(Mono.just(contact), VendorContact.class))
        .switchIfEmpty(notFound().build());
    }

    /**
     * Delete a vendor contact
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return vendorContacts.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    } 
    
    /**
     * Update a vendor contact
     */
    public Mono<ServerResponse> updateVendor(ServerRequest req) {
        var existed = vendorContacts.findByIdVendor(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            VendorContact g = (VendorContact) data[0];
            VendorContact g2 = (VendorContact) data[1];
            if (g2 != null) {
                g.setCompanyName(g2.getCompanyName());
                g.setContactName(g2.getContactName());
                g.setAddress(g2.getAddress());
                g.setContact(g2.getContact());
                g.setContactType(g2.getContactType());
            }
            return g;
        }, existed, req.bodyToMono(VendorContact.class)).cast(VendorContact.class)
                .flatMap(contact -> vendorContacts.updateVendor(contact.getCompanyName(),
                        contact.getContactName(), contact.getAddress(), contact.getContact(),
                        contact.getId()))
                .flatMap(rows -> noContent().build());
    }   
}

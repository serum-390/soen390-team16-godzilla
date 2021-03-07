package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.VenderContactRepository;
import ca.serum390.godzilla.domain.vender.VenderContact;
import reactor.core.publisher.Mono;

@Component
public class VenderContactHandler {

    private final VenderContactRepository venderContacts;

    public VenderContactHandler(VenderContactRepository venderContact) {
        this.venderContacts = venderContact;
    }
    
    /**
     * API for vender
     */

    //get all vender contact
    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(venderContacts.findAllVender(), VenderContact.class);
    }

    //create a vender contact
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(VenderContact.class).flatMap(venderContacts::save).flatMap(id -> noContent().build());
    }

    //get a vender contact
    public Mono<ServerResponse> getVender(ServerRequest req) {
        return venderContacts.findByIdVender(Integer.parseInt(req.pathVariable("id")))
        .flatMap(contact -> ok().body(Mono.just(contact), VenderContact.class))
        .switchIfEmpty(notFound().build());
    }

    // Delete a vender contact
    public Mono<ServerResponse> delete(ServerRequest req) {
        return venderContacts.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    } 
    
    //Update a vender contact
    public Mono<ServerResponse> updateVender(ServerRequest req) {
        var existed = venderContacts.findByIdVender(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            VenderContact g = (VenderContact) data[0];
            VenderContact g2 = (VenderContact) data[1];
            if (g2 != null) {
                g.setCompanyName(g2.getCompanyName());
                g.setContactName(g2.getContactName());
                g.setAddress(g2.getAddress());
                g.setContact(g2.getContact());
                g.setContactType(g2.getContactType());
            }
            return g;
        }, existed, req.bodyToMono(VenderContact.class)).cast(VenderContact.class)
                .flatMap(contact -> venderContacts.updateVender(contact.getCompanyName(),
                        contact.getContactName(), contact.getAddress(), contact.getContact(),
                        contact.getContactType(), contact.getId()))
                .flatMap(rows -> noContent().build());
    }   
    
}

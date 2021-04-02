package ca.serum390.godzilla.api.handlers;

import static java.lang.Integer.parseInt;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.exceptions.NegativeIdException;
import ca.serum390.godzilla.data.repositories.VendorContactRepository;
import ca.serum390.godzilla.domain.vendor.VendorContact;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Component
public class VendorContactHandler {

    private final VendorContactRepository vendorContacts;

    public VendorContactHandler(VendorContactRepository vendorContact) {
        this.vendorContacts = vendorContact;
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
     * @param req
     * @return
     */
    public Mono<ServerResponse> getBy(ServerRequest req){
        Optional<String> name = req.queryParam("name");
        Optional<String> id = req.queryParam("id");

        if (name.isPresent()){
            return queryVendorByName(name.get());
        } else if (id.isPresent()) {
            return queryVendorById(id.get());
        } else {
            return getAllVendor();
        }
    }
    /**
     * get a vendor contact by id
     */
    private Mono<ServerResponse> queryVendorById(String id) {
        // return vendorContacts.findByIdVendor(Integer.parseInt(req.pathVariable("id")))
        // .flatMap(contact -> ok().body(Mono.just(contact), VendorContact.class))
        // .switchIfEmpty(notFound().build());
        return Mono
            .fromCallable(() -> parseInt(id))
            .handle(this::errorIfNegativeId)
            .flatMap(vendorContacts::findByIdVendor)
            .flatMap(vendorContact -> ok().body(Mono.just(vendorContact), VendorContact.class))
            .switchIfEmpty(notFound().build());
            //.onErrorResume(e -> unprocessableEntity().bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
   }

   /**
    * get a vendor by name
    */
    private Mono<ServerResponse> queryVendorByName(String name) {
        return vendorContacts.findByNameVendor(name)
            .collectList()
            .flatMap(l -> l.isEmpty() ? Mono.empty() : Mono.just(l))
            .map(l -> l.size() == 1 ? l.get(0) : l)
            .flatMap(vendorContact -> ok().bodyValue(vendorContact))
            .switchIfEmpty(Mono.defer(() -> status(404).bodyValue("Vendor Contact with name " + name + " does not exist.")));
    }

    /**
     * get all vendor
     */
    private Mono<ServerResponse> getAllVendor(){
        return ok().body(vendorContacts.findAllVendor(), VendorContact.class);
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

    /**
     * Handle if ID is negative
     */
    private void errorIfNegativeId(Integer value, SynchronousSink<Integer> sink){
        if(value < 0){
            sink.error(new NegativeIdException("Negative id " + value + " id prohibited for VendorContact"));
        } else {
            sink.next(value);
        }
    }
}

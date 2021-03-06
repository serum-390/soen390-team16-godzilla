package ca.serum390.godzilla.api.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

import ca.serum390.godzilla.data.repositories.SalesContactRepository;
import ca.serum390.godzilla.domain.orders.SalesContact;
import reactor.core.publisher.Mono;

@Component
public class SalesContactHandler {

    private final SalesContactRepository salesContacts;

    public SalesContactHandler(SalesContactRepository salesContact) {
        this.salesContacts = salesContact;
    }

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(salesContacts.findAll(), SalesContact.class);
    }

    // Create a sales contact
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(SalesContact.class).flatMap(salesContacts::save).flatMap(id -> noContent().build());
    }

    // Get a sales contact
    public Mono<ServerResponse> get(ServerRequest req) {
        return salesContacts.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesContact -> ok().body(Mono.just(salesContact), SalesContact.class))
                .switchIfEmpty(notFound().build());
    }

    // Delete a sales contact
    public Mono<ServerResponse> delete(ServerRequest req) {
        return salesContacts.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    // Update a sales contact
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = salesContacts.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            SalesContact g = (SalesContact) data[0];
            SalesContact g2 = (SalesContact) data[1];
            if (g2 != null) {
                g.setCompanyName(g2.getCompanyName());
                g.setContactName(g2.getContactName());
                g.setAddress(g2.getAddress());
                g.setContact(g2.getContact());
                g.setContactType(g2.getContactType());
            }
            return g;
        }, existed, req.bodyToMono(SalesContact.class)).cast(SalesContact.class)
                .flatMap(salesContact -> salesContacts.update(salesContact.getCompanyName(),
                        salesContact.getContactName(), salesContact.getAddress(), salesContact.getContact(),
                        salesContact.getContactType(), salesContact.getId()))
                .flatMap(rows -> noContent().build());
    }

}
package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.util.BuildableMap.map;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import ca.serum390.godzilla.data.repositories.SalesContactRepository;
import ca.serum390.godzilla.domain.sales.SalesContact;
import reactor.core.publisher.Mono;

@Component
public class SalesContactHandler {

    private final SalesContactRepository salesContact;

    public SalesContactHandler(SalesContactRepository salesContact) {
        this.salesContact = salesContact;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().body(salesContact.findAll(), SalesContact.class);
    }

    // Create a sales order
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(SalesContact.class).flatMap(salesContact::save)
                .flatMap(id -> created(URI.create("/salescontact/" + id)).build());
    }

    // Get a sales order
    public Mono<ServerResponse> get(ServerRequest req) {
        return salesContact.findById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesContact -> ok().body(Mono.just(salesContact), SalesContact.class))
                .switchIfEmpty(notFound().build());
    }

    // Delete a sales order
    public Mono<ServerResponse> delete(ServerRequest req) {
        return salesContact.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    // TODO Update a sales order
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = salesContact.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            SalesContact g = (SalesContact) data[0];
            SalesContact g2 = (SalesContact) data[1];
            if (g2 != null) {
                g.setId(g2.getId());
                g.setCompanyName(g2.getCompanyName());
                g.setContactName(g2.getContactName());
                g.setAddress(g2.getAddress());
                g.setContact(g2.getContact());
                g.setContactType(g2.getContactType());
            }
            return g;
        }, existed, req.bodyToMono(SalesContact.class)).cast(SalesContact.class)
                .flatMap(SalesContact -> salesContact.update(SalesContact.getCompanyName, SalesContact.getContactName(),
                        SalesContact.getAddress(), SalesContact.getContact(), SalesContact.getContactType(),
                        SalesContact.getId()))
                .flatMap(SalesContact -> noContent().build());
    }

}
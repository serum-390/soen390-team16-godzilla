package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.api.handlers.exceptions.NegativeSalesContactIdException.CANNOT_PROCESS_DUE_TO;
import static ca.serum390.godzilla.util.BuildableJsonMap.map;
import static java.lang.Integer.parseInt;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.unprocessableEntity;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.exceptions.NegativeSalesContactIdException;
import ca.serum390.godzilla.data.repositories.SalesContactRepository;
import ca.serum390.godzilla.domain.orders.SalesContact;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Component
@AllArgsConstructor
public class SalesContactHandler {

    private final SalesContactRepository salesContacts;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(salesContacts.findAll(), SalesContact.class);
    }

    /**
     * Add a new {@link SalesContact} to the system.
     *
     * @param req {@link ServerRequest} object containing the new SalesContact
     * @return 200 OK + the new entity created, 422 Unprocessable Entity + error
     *         message if the {@link SalesContact} violates business rules
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req
                .bodyToMono(SalesContact.class)
                .handle(NegativeSalesContactIdException::errorIfNegativeContactId)
                .flatMap(salesContacts::save)
                .flatMap(salesContact -> ok().bodyValue(salesContact))
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    /**
     * Get a sales contact
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> get(ServerRequest req) {
        return Mono
                .fromCallable(() -> parseInt(req.pathVariable("id")))
                .handle(this::errorIfNegativeId)
                .flatMap(salesContacts::findById)
                .flatMap(c -> ok().body(Mono.just(c), SalesContact.class))
                .switchIfEmpty(notFound().build())
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    /**
     * Delete a sales contact
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return Mono
                .fromCallable(() -> parseInt(req.pathVariable("id")))
                .handle(this::errorIfNegativeId)
                .map(salesContacts::deleteById)
                .flatMap(nothing -> noContent().build())
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    private void errorIfNegativeId(Integer value, SynchronousSink<Integer> sink) {
        if (value < 0) {
            sink.error(new NegativeSalesContactIdException(
                "Negative id " +  value + " is prohibited for SalesContact"));
        } else {
            sink.next(value);
        }
    }

    /**
     * Update a sales contact
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        Mono<SalesContact> existed = Mono
                .fromCallable(() -> Integer.parseInt(req.pathVariable("id")))
                .flatMap(salesContacts::findById);

        Mono<SalesContact> received = req
                .bodyToMono(SalesContact.class)
                .handle(NegativeSalesContactIdException::errorIfNegativeContactId);

        return Mono
                .zip(this::combineSalesContacts, existed, received)
                .flatMap(this::saveSalesContact)
                .flatMap(rows -> ok()
                    .body(map("rowsUpdated", rows).toMono(), Map.class))
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    private SalesContact combineSalesContacts(Object[] contacts) {
        SalesContact g = (SalesContact) contacts[0];
        SalesContact g2 = (SalesContact) contacts[1];
        if (g2 != null) {
            g.setCompanyName(g2.getCompanyName());
            g.setContactName(g2.getContactName());
            g.setAddress(g2.getAddress());
            g.setContact(g2.getContact());
            g.setContactType(g2.getContactType());
        }
        return g;
    }

    private Mono<Integer> saveSalesContact(SalesContact salesContact) {
        return salesContacts.update(
                salesContact.getCompanyName(),
                salesContact.getContactName(),
                salesContact.getAddress(),
                salesContact.getContact(),
                salesContact.getContactType(),
                salesContact.getId());
    }
}
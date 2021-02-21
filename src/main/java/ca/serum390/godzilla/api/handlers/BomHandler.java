package ca.serum390.godzilla.api.handlers;

import java.net.URI;
import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.data.repositories.BomRepository;
import ca.serum390.godzilla.domain.billOfMaterial.BomEntry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class BomHandler {

    @Autowired
    BomRepository boms;

    public BomHandler(BomRepository boms){
        this.boms = boms;
    }

    /**
     * Insert into the table
     */
    public Mono<ServerResponse> insert(ServerRequest req){
        return req.bodyToMono(BomEntry.class)
            .flatMap(boms::save)
            .flatMap(id -> created(URI.create("/bom/" + id)).build());
    }

    /**
     * Get all
     */
    public Mono<ServerResponse> getAll(ServerRequest req){
        return ok().body(
            boms.findAll(),
            BomEntry.class
        );
    }

    /**
     * Delete by id
     */
    public Mono<ServerResponse> deleteById(ServerRequest req){
        return boms.deleteById(
            Integer.parseInt(req.pathVariable("item_name"))
        ).flatMap(deleted -> noContent().build());
    }

    /**
     * Get all by ID
     */
    public Mono<ServerResponse> findAllByID(ServerRequest req){
        Integer id = Integer.parseInt(req.pathVariable("item_name"));
        return ok().body(
            boms.findAllById(List.of(id)),
            BomEntry.class
        );
    }
}

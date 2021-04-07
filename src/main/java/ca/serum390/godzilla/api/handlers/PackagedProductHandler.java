package ca.serum390.godzilla.api.handlers;

import static ca.serum390.godzilla.api.handlers.exceptions.NegativePackagedProductIdException.CANNOT_PROCESS_DUE_TO;
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

import ca.serum390.godzilla.api.handlers.exceptions.NegativePackagedProductIdException;
import ca.serum390.godzilla.data.repositories.PackagedProductRepository;
import ca.serum390.godzilla.domain.packaging.PackagedProduct;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Component
@AllArgsConstructor
public class PackagedProductHandler {

    private final PackagedProductRepository packagedProductRepository;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(packagedProductRepository.findAll(), PackagedProduct.class);
    }

    /**
     * Create a packaged product
     * 
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(PackagedProduct.class)
                .flatMap(product -> packagedProductRepository.save(product.getLength(), product.getWidth(),
                        product.getHeight(), product.getWeight(), product.getPackageType(), product.getPackageDate()))
                .flatMap(id -> noContent().build());
    }

    /**
     * Get a packaged product
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> get(ServerRequest req) {
        return Mono.fromCallable(() -> parseInt(req.pathVariable("id"))).handle(this::errorIfNegativeId)
                .flatMap(packagedProductRepository::findById)
                .flatMap(c -> ok().body(Mono.just(c), PackagedProduct.class)).switchIfEmpty(notFound().build())
                .onErrorResume(e -> unprocessableEntity().bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    /**
     * Delete a packaged product
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return packagedProductRepository.deleteById(Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    private void errorIfNegativeId(Integer value, SynchronousSink<Integer> sink) {
        if (value < 0) {
            sink.error(new NegativePackagedProductIdException(
                    "Negative id " + value + " is prohibited for a packaged product"));
        } else {
            sink.next(value);
        }
    }

    /**
     * Update a packaged contact
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = packagedProductRepository.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(data -> {
            PackagedProduct g = (PackagedProduct) data[0];
            PackagedProduct g2 = (PackagedProduct) data[1];
            if (g2 != null) {
                g.setLength(g2.getLength());
                g.setWidth(g2.getWidth());
                g.setHeight(g2.getHeight());
                g.setWeight(g2.getWeight());
                g.setPackageType(g2.getPackageType());
                g.setPackageDate(g2.getPackageDate());
            }
            return g;
        }, existed, req.bodyToMono(PackagedProduct.class)).cast(PackagedProduct.class)
                .flatMap(packagedProduct -> packagedProductRepository.update(
                    packagedProduct.getLength(), 
                    packagedProduct.getWidth(),
                    packagedProduct.getHeight(), 
                    packagedProduct.getWeight(), 
                    packagedProduct.getPackageType(),
                    packagedProduct.getPackageDate(), 
                    packagedProduct.getId()))
                .flatMap(packagedProduct -> noContent().build());
    }

}
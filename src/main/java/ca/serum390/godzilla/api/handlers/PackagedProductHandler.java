package ca.serum390.godzilla.api.handlers;

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

import ca.serum390.godzilla.data.repositories.PackagedProductRepository;
import ca.serum390.godzilla.domain.packaging.PackagedProduct;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Component
@AllArgsConstructor
public class SalesContactHandler {

    private final PackagedProductRepository packagedProduct;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(packagedProduct.findAll(), PackagedProduct.class);
    }

    /**
     * Add a new {@link PackagedProduct} to the system.
     *
     * @param req {@link ServerRequest} object containing the new PackagedProduct
     * @return 200 OK + the new entity created, 422 Unprocessable Entity + error
     *         message if the {@link PackagedProduct} violates business rules
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req
                .bodyToMono(PackagedProduct.class)
                .handle(NegativePackagedProductIdException::errorIfNegativevePackageId)
                .flatMap(packagedProduct::save)
                .flatMap(packagedProduct -> ok().bodyValue(packagedProduct))
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    /**
     * Get a packaged product
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> get(ServerRequest req) {
        return Mono
                .fromCallable(() -> parseInt(req.pathVariable("id")))
                .handle(this::errorIfNegativeId)
                .flatMap(packagedProduct::findById)
                .flatMap(c -> ok().body(Mono.just(c), PackagedProduct.class))
                .switchIfEmpty(notFound().build())
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    /**
     * Delete a packaged product
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return Mono
                .fromCallable(() -> parseInt(req.pathVariable("id")))
                .handle(this::errorIfNegativeId)
                .map(packagedProduct::deleteById)
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
     * Update a packaged contact
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        Mono<PackagedProduct> existed = Mono
                .fromCallable(() -> Integer.parseInt(req.pathVariable("id")))
                .flatMap(packagedProduct::findById);

        Mono<PackagedProduct> received = req
                .bodyToMono(PackagedProduct.class)
                .handle(NegativePackagedProductIdException::errorIfNegativePackageId);

        return Mono
                .zip(this::combinePackagedProducts, existed, received)
                .flatMap(this::savePackagedProduct)
                .flatMap(rows -> ok()
                    .body(map("rowsUpdated", rows).toMono(), Map.class))
                .onErrorResume(e -> unprocessableEntity()
                    .bodyValue(CANNOT_PROCESS_DUE_TO + e.getMessage()));
    }

    private PackagedProduct combinePackagedProducts(Object[] products) {
        PackagedProduct g = (PackagedProduct) products[0];
        PackagedProduct g2 = (PackagedProduct) products[1];
        if (g2 != null) {
            g.setLength(g2.length());
            g.setWidth(g2.getWidth());
            g.setHeight(g2.getHeight());
            g.setWeight(g2.getWeight());
            g.setPackageType(g2.getPackageType());
        }
        return g;
    }

    private Mono<Integer> savePackagedProduct(PackagedProduct packagedProduct) {
        return packagedProduct.update(
                packagedProduct.getLength(),
                packagedProduct.getWidth(),
                packagedProduct.getHeight(),
                packagedProduct.getWeight(),
                packagedProduct.getPackageType(),
                packagedProduct.getId());
    }
}
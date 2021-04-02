package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ShippingHandler {

    private final ShippingRepository shippingRepository;

    public ShippingHandler(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    /**
     * Insert the shipping item into the table
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Shipping.class)
                .flatMap(shippingRepository::save)
                .flatMap(id -> noContent().build());
    }

    /**
     * Delete shipping item in table by id
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return shippingRepository.deleteById(
                Integer.parseInt(req.pathVariable("id")))
                .flatMap(deleted -> noContent().build());
    }

    /**
     * @param req
     * @return all the shipping items in the repo
     */
    public Mono<ServerResponse> get(ServerRequest req) {
        return ok().body(shippingRepository.findAll(), Shipping.class);
    }

    /**
     * updates the shipping item
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = shippingRepository.findById(Integer.parseInt(req.pathVariable("id")));
        return Mono.zip(
                data -> {
                    Shipping original = (Shipping) data[0];
                    Shipping updated = (Shipping) data[1];
                    if (updated != null) {
                        original.setShippingDate(updated.getShippingDate());
                        original.setDueDate(updated.getDueDate());
                        original.setStatus(updated.getStatus());
                        original.setOrderID(updated.getOrderID());
                        original.setShippingPrice(updated.getShippingPrice());
                        original.setShippingMethod(updated.getShippingMethod());

                    }
                    return original;
                },
                existed,
                req.bodyToMono(Shipping.class)
        ).cast(Shipping.class)
                .flatMap(shippingItem -> shippingRepository.update(
                        shippingItem.getId(),
                        shippingItem.getShippingMethod(),
                        shippingItem.getStatus(),
                        shippingItem.getDueDate(),
                        shippingItem.getShippingDate(),
                        shippingItem.getOrderID(),
                        shippingItem.getShippingPrice()))
                .flatMap(item -> noContent().build());
    }
}
package ca.serum390.godzilla.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.manufacturing.Good;
import ca.serum390.godzilla.repository.GoodsRepository;
import reactor.core.publisher.Mono;

@Component
public class GoodsHandler {

    /**
     * ??? NOTE:
     * This {@link GoodsRepository} parameter is injected into the class
     * by the Spring Inversion of Control container's dependency injection
     * facilities.
     * ???
     */
    private final GoodsRepository goods;

    public GoodsHandler(GoodsRepository goods) {
        this.goods = goods;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().body(
                goods.findAll(),
                Good.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Good.class)
                  .flatMap(goods::save)
                  .flatMap(id -> created(URI.create("/goods/" + id)).build());
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return goods.findById(
                UUID.fromString(req.pathVariable("id")))
                    .flatMap(good -> ok().body(Mono.just(good), Good.class))
                    .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        var existed = goods.findById(UUID.fromString(req.pathVariable("id")));
        return Mono.zip(
            data -> {
                Good g = (Good) data[0];
                Good g2 = (Good) data[1];
                if (g2 != null && StringUtils.hasText(g2.getName())) {
                    g.setName(g2.getName());
                }
                if (g2 != null && StringUtils.hasText(g2.getDescription())) {
                    g.setDescription(g2.getDescription());
                }
                return g;
            },
            existed,
            req.bodyToMono(Good.class)
        ).cast(Good.class)
            .flatMap(good -> goods.update(good.getName(),
                                          good.getDescription(),
                                          good.getId()))
            .flatMap(good -> noContent().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return goods.deleteById(
            UUID.fromString(req.pathVariable("id"))
        ).flatMap(deleted -> noContent().build());
    }
}

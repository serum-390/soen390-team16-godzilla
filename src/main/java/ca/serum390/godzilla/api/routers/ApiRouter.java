package ca.serum390.godzilla.api.routers;

import ca.serum390.godzilla.api.handlers.ProductionHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static ca.serum390.godzilla.util.BuildableJsonMap.map;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static reactor.core.publisher.Mono.fromCallable;

@Configuration
@AllArgsConstructor
public class ApiRouter implements WebFluxConfigurer {

    public static final String ALL_GOOD_IN_THE_HOOD = "All good in the hood\n";

    ProductionHandler productionHandler;
    RouterFunction<ServerResponse> goodsRoute;
    RouterFunction<ServerResponse> inventoryRoute;
    RouterFunction<ServerResponse> orderRoute;
    RouterFunction<ServerResponse> salesContactRoute;
    RouterFunction<ServerResponse> vendorContactRoute;
    RouterFunction<ServerResponse> plannedProductRoute;
    RouterFunction<ServerResponse> productionManagerRoute;
<<<<<<< HEAD
    RouterFunction<ServerResponse> godzillaUserRoute;
=======
    RouterFunction<ServerResponse> shippingRoute;
    RouterFunction<ServerResponse> shippingManagerRoute;
>>>>>>> b5701fdbb7efeb7407500c92c02cea71682dd5a2

    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .path("/api", apiBuilder -> apiBuilder
                        .GET("/materials", productionHandler::demoMaterials)
                        .GET("/products", productionHandler::demoProducts)
                        .GET("/healthcheck", this::healthCheck)
                        .GET("/docs", this::docs)
                        .add(goodsRoute)
                        .add(inventoryRoute)
                        .add(orderRoute)
                        .add(salesContactRoute)
                        .add(vendorContactRoute)
                        .add(plannedProductRoute)
                        .add(productionManagerRoute)
<<<<<<< HEAD
                        .add(godzillaUserRoute)
=======
                        .add(shippingRoute)
                        .add(shippingManagerRoute)
>>>>>>> b5701fdbb7efeb7407500c92c02cea71682dd5a2
                        .build())
                .build();
    }

    /**
     * Serve some static resources via /resources/<my_resource>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/", "classpath:/static/resources/");
        registry.addResourceHandler("/api/docs/**")
                .addResourceLocations("classpath:/static/docs/");
    }

    private Mono<ServerResponse> healthCheck(ServerRequest request) {
        return ok().bodyValue(ALL_GOOD_IN_THE_HOOD);
    }

    private Mono<ServerResponse> docs(ServerRequest request) {
        return ok().contentType(TEXT_HTML)
                .body(fromCallable(
                        () -> new ClassPathResource("static/docs/index.html")),
                        Resource.class)
                .onErrorResume(e -> status(404).body(
                        fromCallable(() -> map("error", e.getMessage())), Map.class));
    }
}

package ca.serum390.godzilla.api.routers;

import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromCallable;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.ProductionHandler;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@AllArgsConstructor
public class ApiRouter implements WebFluxConfigurer {

    public static final String ALL_GOOD_IN_THE_HOOD = "All good in the hood\n";

    ProductionHandler productionHandler;
    RouterFunction<ServerResponse> goodsRoute;
    RouterFunction<ServerResponse> usersRoute;
    RouterFunction<ServerResponse> orderRoute;
    RouterFunction<ServerResponse> inventoryRoute;
    RouterFunction<ServerResponse> salesContactRoute;
    RouterFunction<ServerResponse> vendorContactRoute;
    RouterFunction<ServerResponse> plannedProductRoute;
    RouterFunction<ServerResponse> productionManagerRoute;
    RouterFunction<ServerResponse> packagedRoute;
    RouterFunction<ServerResponse> shippingRoute;
    RouterFunction<ServerResponse> shippingManagerRoute;
    RouterFunction<ServerResponse> godzillaUserRoute;


    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route() {
      
        return RouterFunctions.route()
  .path("/api", apiBuilder -> apiBuilder
        .GET("/docs", this::docs)
        .GET("/healthcheck", this::healthCheck)
        .GET("/products", productionHandler::demoProducts)
        .GET("/materials", productionHandler::demoMaterials)
        .add(usersRoute)
        .add(goodsRoute)
        .add(orderRoute)
        .add(shippingRoute)
        .add(inventoryRoute)
        .add(salesContactRoute)
        .add(vendorContactRoute)
        .add(plannedProductRoute)
        .add(packagedRoute)
        .add(shippingManagerRoute)
        .add(productionManagerRoute).build())
  
              .build();

    }

    /**
     * Serve some static resources via /resources/<my_resource>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        var cc = CacheControl.maxAge(Duration.ofDays(30)).staleIfError(Duration.ofMinutes(10));

        registry.addResourceHandler("/resources/**").setCacheControl(cc).addResourceLocations("/public",
                "classpath:/static/", "classpath:/static/resources/");

        registry.addResourceHandler("/api/docs/**").setCacheControl(cc).addResourceLocations("classpath:/static/docs/");

        registry.addResourceHandler("/static/**").setCacheControl(cc).addResourceLocations("classpath:/static/static/");
    }

    private Mono<ServerResponse> healthCheck(ServerRequest request) {
        return ok().bodyValue(ALL_GOOD_IN_THE_HOOD);
    }

    private Mono<ServerResponse> docs(ServerRequest request) {
        return ok().contentType(TEXT_HTML)
                .body(fromCallable(() -> new ClassPathResource("static/docs/index.html")), Resource.class)
                .onErrorResume(e -> notFound().build());
    }
}

package ca.serum390.godzilla.configuration.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.experimental.HelloHandler;
import ca.serum390.godzilla.handlers.InventoryHandler;
import ca.serum390.godzilla.handlers.SalesHandler;

@Configuration
public class ApiRouter implements WebFluxConfigurer {

    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route(
            RouterFunction<ServerResponse> goodsRoute,
            HelloHandler helloHandler) {
        return RouterFunctions.route()
                .path("/api", apiBuilder -> apiBuilder
                    .GET("/inv", InventoryHandler::demoInventory)
                    .GET("/sales", SalesHandler::demoSales)
                    .GET("/hello", helloHandler::helloWorld)
                    .add(goodsRoute)
                    .build())
                .build();
    }

    /**
     * Serve some static resources via /resources/<my_resource>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/");
    }
}

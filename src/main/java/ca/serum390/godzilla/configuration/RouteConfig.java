package ca.serum390.godzilla.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.handlers.HelloHandler;
import ca.serum390.godzilla.handlers.InventoryHandler;

@Configuration
public class RouteConfig implements WebFluxConfigurer {

    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .path("/api/", builder -> builder
                    .GET("/inv/", InventoryHandler::getInventory)
                    .GET("/hello/", HelloHandler::helloWorld))
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

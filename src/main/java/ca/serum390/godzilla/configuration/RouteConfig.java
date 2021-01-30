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

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .path("/api/", builder -> builder
                    .GET("/inv/", InventoryHandler::getInventory)
                    .GET("/hello/", HelloHandler::helloWorld))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/");
    }
}

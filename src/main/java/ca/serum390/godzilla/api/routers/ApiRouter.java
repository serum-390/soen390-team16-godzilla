package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.InventoryHandler;
import ca.serum390.godzilla.api.handlers.SalesHandler;
import ca.serum390.godzilla.api.handlers.BomHandler;
import ca.serum390.godzilla.util.experimental.HelloHandler;


@Configuration
public class ApiRouter implements WebFluxConfigurer {

    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route(
            SalesHandler salesHandler,
            HelloHandler helloHandler,
            InventoryHandler inventoryHandler,
            BomHandler bomHandler,
            RouterFunction<ServerResponse> goodsRoute,
            RouterFunction<ServerResponse> inventoryRoute,
            RouterFunction<ServerResponse> bomRoute) {

        return RouterFunctions.route()
                .path("/api", apiBuilder -> apiBuilder
                    .GET("/sales", salesHandler::demoSales)
                    .GET("/hello", helloHandler::helloWorld)
                    .add(goodsRoute)
                    .add(inventoryRoute)
                    .add(bomRoute)
                    .build())
                .build();
    }

    /**
     * Serve some static resources via /resources/<my_resource>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public",
                                      "classpath:/static/",
                                      "classpath:/static/resources/");
    }
}

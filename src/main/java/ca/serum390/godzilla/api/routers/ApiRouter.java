package ca.serum390.godzilla.api.routers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.ProductionHandler;
import ca.serum390.godzilla.api.handlers.BomHandler;
import ca.serum390.godzilla.api.handlers.InventoryHandler;
import ca.serum390.godzilla.api.handlers.SalesOrderHandler;
import ca.serum390.godzilla.api.handlers.ContactHandler;

@Configuration
public class ApiRouter implements WebFluxConfigurer {


    public static final String ALL_GOOD_IN_THE_HOOD = "All good in the hood";

    /**
     * Router using the functional endpoints Spring WebFlux API
     *
     * @return A router function bound to the application's RESTful APIs.
     */
    @Bean
    public RouterFunction<ServerResponse> route(
            SalesOrderHandler salesHandler,
            InventoryHandler inventoryHandler,
            BomHandler bomHandler,
            ProductionHandler productionHandler,
            VenderContactRouter VenderContactHandler,
            RouterFunction<ServerResponse> goodsRoute,
            RouterFunction<ServerResponse> inventoryRoute,
            RouterFunction<ServerResponse> bomRoute,
            RouterFunction<ServerResponse> salesOrderRoute,
            RouterFunction<ServerResponse> salesContactRoute,
            RouterFunction<ServerResponse> venderContactRoute)
    {

        return RouterFunctions.route()
                .path("/api", apiBuilder -> apiBuilder
                    .GET("/materials", productionHandler::demoMaterials)
                    .GET("/products", productionHandler::demoProducts)
                    .GET("/healthcheck", req -> ok().bodyValue(ALL_GOOD_IN_THE_HOOD))
                    .add(goodsRoute)
                    .add(inventoryRoute)
                    .add(salesOrderRoute)
                    .add(salesContactRoute)
                    .add(bomRoute)
                    .add(venderContactRoute)
                    .build())
                .build();
    }

        /**
         * Serve some static resources via /resources/<my_resource>
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/resources/**").addResourceLocations("/public", "classpath:/static/",
                                "classpath:/static/resources/");
        }
}

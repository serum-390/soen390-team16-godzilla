package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.VendorContactHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class VendorContactRouter {

    VendorContactHandler vendorContactHandler;

    @Bean
    public RouterFunction<ServerResponse> vendorContactRoute() {
        return RouterFunctions.route()
                .path("/vendorcontact/", builder -> builder
                    .GET("/", vendorContactHandler::getBy)
                    .POST("/", vendorContactHandler::create)
                    .PUT("/{id}", vendorContactHandler::updateVendor)
                    .DELETE("/{id}", vendorContactHandler::delete))
                .build();
    }
}

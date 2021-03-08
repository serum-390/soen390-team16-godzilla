package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.VendorContactHandler;

@Configuration
public class VendorContactRouter {
    @Bean
    public RouterFunction<ServerResponse> vendorContactRoute(VendorContactHandler vendorContactHandler) {
        final String ID = "/{id}";
        return RouterFunctions.route()
                    .path("/vendorcontact/",
                        builder -> builder.GET("/", vendorContactHandler::all)
                        .POST("/", vendorContactHandler::create)
                                .GET(ID, vendorContactHandler::getVendor)
                                .PUT(ID, vendorContactHandler::updateVendor)
                                .DELETE(ID, vendorContactHandler::delete))
                .build();
    }
}

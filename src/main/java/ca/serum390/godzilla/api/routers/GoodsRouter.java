package ca.serum390.godzilla.api.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.api.handlers.GoodsHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GoodsRouter {

    GoodsHandler goodsHandler;

    @Bean
    public RouterFunction<ServerResponse> goodsRoute() {
        final String ID = "/{id}";
        return RouterFunctions.route()
                .path("/goods/", builder -> builder
                    .GET("/", goodsHandler::all)
                    .POST("/", goodsHandler::create)
                    .GET(ID, goodsHandler::get)
                    .PUT(ID, goodsHandler::update)
                    .DELETE(ID, goodsHandler::delete))
                .build();
    }
}

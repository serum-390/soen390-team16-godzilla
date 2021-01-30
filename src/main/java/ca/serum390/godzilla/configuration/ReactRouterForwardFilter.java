package ca.serum390.godzilla.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


/**
 * This {@link WebFilter} component is needed to forward requests to the React
 * Router routes.
 */
@Component
public class ReactRouterForwardFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (matchesReactRouterRoute(exchange)) {
            return forwardToReactRouter(exchange, chain);
        }

        return chain.filter(exchange);
    }

    /**
     * ! NOTE: This is probably not the most effecient way to forward all these
     * ! requests to the react-router - we are basically checking each and every
     * ! request to see if it needs to be forwarded to index.html
     *
     * @param exchange
     * @return
     */
    private boolean matchesReactRouterRoute(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return !path.startsWith("/api/")
            && !path.startsWith("/resources/")
            && !path.endsWith(".js")
            && !path.endsWith(".css")
            && !path.endsWith(".svg");
    }

    private Mono<Void> forwardToReactRouter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(
                exchange.mutate()
                        .request(
                            exchange.getRequest()
                                    .mutate()
                                    .path("/index.html")
                                    .build()
                        ).build());
    }
}

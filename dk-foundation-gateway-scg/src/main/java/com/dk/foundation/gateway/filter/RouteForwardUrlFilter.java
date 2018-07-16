package com.dk.foundation.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.regex.Pattern;

@Component
public class RouteForwardUrlFilter implements GlobalFilter, Ordered {
    private static final Log log = LogFactory.getLog(RouteForwardUrlFilter.class);
    public static final int ROUTE_FORWARD_FILTER_ORDER = 10001;
    private static final String SCHEME_REGEX = "[a-zA-Z]([a-zA-Z]|\\d|\\+|\\.|-)*:.*";
    static final Pattern schemePattern = Pattern.compile("[a-zA-Z]([a-zA-Z]|\\d|\\+|\\.|-)*:.*");

    public RouteForwardUrlFilter() {
    }

    public int getOrder() {
        return 10001;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return chain.filter(exchange);
        } else {
            log.trace("RouteToRequestUrlFilter start");
            URI uri = exchange.getRequest().getURI();
            String rawPath = uri.getRawPath();
            boolean encoded = ServerWebExchangeUtils.containsEncodedParts(uri);
            URI routeUri = route.getUri();
            if (hasAnotherScheme(routeUri)) {
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR, routeUri.getScheme());
                routeUri = URI.create(routeUri.getSchemeSpecificPart());
            }

            URI requestUrl = UriComponentsBuilder.fromUri(uri).uri(routeUri).path(rawPath).build(encoded).toUri();
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
            return chain.filter(exchange);
        }
    }

    static boolean hasAnotherScheme(URI uri) {
        return schemePattern.matcher(uri.getSchemeSpecificPart()).matches() && uri.getHost() == null && uri.getRawPath() == null;
    }
}
package com.coupons.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import static com.coupons.gateway.util.JwtUtil.validateToken;

@Component
@RequiredArgsConstructor
public class JwtValidationFilter implements GatewayFilterFactory<JwtValidationFilter.Config> {
    private final RouteValidator validator;

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String jwtToken = authorizationHeader.substring(7);
                    if (validateToken(jwtToken)) {
                        return chain.filter(exchange);
                    }
                }
            }
            final ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        };
    }
    public static class Config {
        // You can define any configuration properties needed for the filter
    }

}

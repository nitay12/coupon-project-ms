package com.coupons.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    private static final List<String> openApiEndpoints = List.of(
            "/auth/signin",
            "/auth/signup"
    );

    public final Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri ->
                            request.getURI()
                                    .getPath()
                                    .contains(uri));
}

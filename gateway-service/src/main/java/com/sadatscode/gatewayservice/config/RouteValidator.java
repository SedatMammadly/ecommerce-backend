package com.sadatscode.gatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public List<String>openApiEndpoints = List.of(
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request->openApiEndpoints.stream()
                    .noneMatch(uri-> request.getURI().getPath().contains(uri));
}

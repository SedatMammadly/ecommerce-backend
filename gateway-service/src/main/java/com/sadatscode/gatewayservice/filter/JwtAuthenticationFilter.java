//package com.sadatscode.gatewayservice.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//
//public class JwtAuthenticationFilter implements GatewayFilter {
//    private final WebClient webClient;
//
//    public JwtAuthenticationFilter(WebClient.Builder webClient) {
//        this.webClient = webClient.build();
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//       ServerHttpRequest request = exchange.getRequest();
//        List<String> apiEndpoints = List.of("/api/v1/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs");
//        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream().noneMatch(uri -> r.getURI().getPath().startsWith(uri));
//
//        if (isApiSecured.test(request)) {
//            if (authMissing(request)) {
//                System.out.println("not authenticated");
//                return onError(exchange);
//            }
//
//            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//            if (token == null || !token.startsWith("Bearer ")) {
//                return onError(exchange);
//            }
//                token = token.substring(7);
//
//
//          return webClient.get()
//                    .uri("http://security-service/api/v1/auth/validToken/" + token)
//                    .retrieve()
//                    .bodyToMono(Boolean.class)
//                   .flatMap(isTokenValid->{
//                       if(Boolean.FALSE.equals(isTokenValid)) {
//                           return onError(exchange);
//                       }
//                       return chain.filter(exchange);
//                   });
//
//        }
//        return chain.filter(exchange);
//    }
//
//    private boolean authMissing(ServerHttpRequest request) {
//        boolean missing = !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
//        if (missing) {
//            System.out.println("Authorization header is missing");
//        }
//        return missing;
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//
//}

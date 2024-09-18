//package com.sadatscode.gatewayservice.config;
//
//import com.sadatscode.gatewayservice.filter.JwtAuthenticationFilter;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class RouteConfig {
////    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter(WebClient.Builder webClient) {
//        return new JwtAuthenticationFilter(webClient);
//    }
//
//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder routeLocatorBuilder, JwtAuthenticationFilter jwtAuthenticationFilter) {
//        return routeLocatorBuilder.routes()
//                .route("security-service",s -> s.path("/api/v1/auth/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter))
//                        .uri("lb://SECURITY-SERVICE"))
//                .route("product-service",p -> p.path("/api/v1/product/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter))
//                        .uri("lb://PRODUCT-SERVICE"))
//                .route("store-service",s -> s.path("/api/v1/store/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter))
//                        .uri("lb://STORE-SERVICE"))
//                .route("order-service",s -> s.path("/api/v1/order/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter))
//                        .uri("lb://ORDER-SERVICE"))
//                .build();
//    }
//}

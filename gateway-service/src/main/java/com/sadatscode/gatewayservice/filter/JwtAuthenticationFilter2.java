package com.sadatscode.gatewayservice.filter;

import com.sadatscode.gatewayservice.config.RouteValidator;
import com.sadatscode.gatewayservice.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter2 extends AbstractGatewayFilterFactory<JwtAuthenticationFilter2.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter2(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("JwtAuthenticationFilter2 applied for: " + exchange.getRequest().getPath());
            if (validator.isSecured.test(exchange.getRequest())){
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = null;
                if(authHeader!=null || authHeader.startsWith("Bearer ")){
                    token = authHeader.substring(7);
                }
                try {
                   jwtUtil.validateToken(token);
                }catch (Exception e){
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}

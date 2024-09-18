package com.sadatscode.securityservice.config;

import com.sadatscode.securityservice.authentication.service.JwtService;
import com.sadatscode.securityservice.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwt;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
     String authHeader = request.getHeader("Authorization");
     String token;
     String username;
     if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          filterChain.doFilter(request,response);
          return;
     }
     token = authHeader.replace("Bearer ", "");
     username= jwt.extractUsername(token);
     if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        var isTokenValid = tokenRepository.findByToken(token).map(t -> !t.getRevoked() && !t.getExpired()).orElse(false);
         if(jwt.isTokenValid(token, userDetails) && isTokenValid) {
             UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                     userDetails,
                     null,
                     userDetails.getAuthorities());
             authenticationToken.setDetails(userDetails);
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }
     }
     filterChain.doFilter(request,response);
    }
}

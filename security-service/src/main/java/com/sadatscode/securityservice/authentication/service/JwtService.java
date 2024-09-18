package com.sadatscode.securityservice.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final PasswordEncoder passwordEncoder;

    private static final String SecretKey="qGIXg+zrYlQ60V41zSkodeK5u8pnP3TTXO1TidU2OFJCFMqtVIr7dJxQh52JUC0p\n" +
            "Q0E2G7v+tbnZc1qvgBicKQeoJsLoRtsr3WbTNxq8m60iLn8nP4QAy+W8rebFI24p\n" +
            "gcUSnJFzSzD70vGdUC8RKVdZffJYcvZRpqqW01/WB9o=";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        claims.put("password", passwordEncoder.encode(userDetails.getPassword()));
        claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims,userDetails);
    }

    private String createToken(Map<String,Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 60*1000*24))
                .signWith(SignatureAlgorithm.HS512, getSignInKey())
                .compact();
    }
    public String extractUsername(String token) {
       return extractClaim(token, Claims::getSubject);
    }

    private Claims  extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim (String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SecretKey.replaceAll("\\n", ""));
       return new SecretKeySpec(keyBytes,SignatureAlgorithm.HS512.getJcaName());

    }
}

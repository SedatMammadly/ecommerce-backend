package com.sadatscode.gatewayservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {
    private static final String SecretKey="qGIXg+zrYlQ60V41zSkodeK5u8pnP3TTXO1TidU2OFJCFMqtVIr7dJxQh52JUC0p" +
            "Q0E2G7v+tbnZc1qvgBicKQeoJsLoRtsr3WbTNxq8m60iLn8nP4QAy+W8rebFI24p" +
            "gcUSnJFzSzD70vGdUC8RKVdZffJYcvZRpqqW01/WB9o=";

    public void validateToken(String token) {
         Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

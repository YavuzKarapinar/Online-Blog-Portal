package me.jazzy.obp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtGenerator {

    public String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey())
                .compact();
    }

    private SecretKey secretKey() {
        String key = "jaskjmsdfkj123jksadas.!'^141'asd";
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> function) {
        Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token) {

        try {

            if(!isExpired(token)) {
                Jwts.parser()
                        .verifyWith(secretKey())
                        .build()
                        .parseSignedClaims(token);
                return true;
            }

        } catch (JwtException e) {
            throw new JwtException("Token Expired");
        }

        return false;
    }

    public boolean isExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}

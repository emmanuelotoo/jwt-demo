package dev.emmanuelotoo.jwtdemo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    // Initializes the key after the class is instantiated and expiration is injected,
    // preventing the repeated creation of the key and enhancing performance
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Generating JWT token
    public String generateToken(String email) {
        return Jwts.builder()
                .claims()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .and()
                .signWith(key)
                .compact();

    }

    // Getting email from JWT token
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validate JWT token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty" + e.getMessage());
        }
        return false;
    }

}
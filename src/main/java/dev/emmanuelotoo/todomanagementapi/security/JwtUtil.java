package dev.emmanuelotoo.todomanagementapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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


}

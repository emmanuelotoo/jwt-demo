package dev.emmanuelotoo.jwtdemo.repository;

import dev.emmanuelotoo.jwtdemo.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
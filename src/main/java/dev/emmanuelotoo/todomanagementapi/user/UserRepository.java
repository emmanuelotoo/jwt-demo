package dev.emmanuelotoo.todomanagementapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
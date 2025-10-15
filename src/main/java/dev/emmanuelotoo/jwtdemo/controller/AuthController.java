package dev.emmanuelotoo.jwtdemo.controller;

import dev.emmanuelotoo.jwtdemo.model.User;
import dev.emmanuelotoo.jwtdemo.repository.UserRepository;
import dev.emmanuelotoo.jwtdemo.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Already exists";
        }
        // Creating a new user's account
        User newUser = new User(
                null,
                user.getName(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );
        userRepository.save(newUser);
        return "User registered successfully";
    }




}

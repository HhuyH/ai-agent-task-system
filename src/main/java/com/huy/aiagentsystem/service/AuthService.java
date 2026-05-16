package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.response.AuthResponse;
import com.huy.aiagentsystem.entity.User;
import com.huy.aiagentsystem.repository.UserRepository;

import com.huy.aiagentsystem.exception.AuthException;

import com.huy.aiagentsystem.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse register(
            String email,
            String password
    ) {

        boolean emailExists = userRepository.findByEmail(email).isPresent();

        if (emailExists) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();

        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(
            String email,
            String password
    ) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        boolean passwordMatches = passwordEncoder.matches(
                    password,
                    user.getPassword()
                );

        if (!passwordMatches) {
            throw new AuthException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
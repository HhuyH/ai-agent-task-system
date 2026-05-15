package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.AuthResponse;
import com.huy.aiagentsystem.entity.User;
import com.huy.aiagentsystem.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(
            String email,
            String password
    ) {

        boolean emailExists =
                userRepository.findByEmail(email).isPresent();

        if (emailExists) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword =
                passwordEncoder.encode(password);

        User user = new User();

        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("USER");

        userRepository.save(user);

        return new AuthResponse("Register successful");
    }

    public AuthResponse login(
            String email,
            String password
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        password,
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        return new AuthResponse("Login successful");
    }
}
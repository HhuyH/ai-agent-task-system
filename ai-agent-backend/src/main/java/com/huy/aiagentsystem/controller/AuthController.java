package com.huy.aiagentsystem.controller;

import com.huy.aiagentsystem.dto.response.AuthResponse;
import com.huy.aiagentsystem.dto.request.LoginRequest;
import com.huy.aiagentsystem.dto.request.RegisterRequest;
import com.huy.aiagentsystem.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(
                request.getEmail(),
                request.getPassword()
        );
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}
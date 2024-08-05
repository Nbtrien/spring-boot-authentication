package com.example.api.controller;

import com.example.api.dto.authentication.LoginRequestDto;
import com.example.api.dto.authentication.RefreshTokenRequestDto;
import com.example.api.dto.authentication.RegisterRequestDto;
import com.example.api.service.AuthenticationService;
import com.example.api.service.RefresherTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RefresherTokenService refresherTokenService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.authenticate(requestDto));
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.register(requestDto));
    }

    @PostMapping("/refresh-token")
    private ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDto requestDto) {
        return ResponseEntity.ok(refresherTokenService.refreshToken(requestDto));
    }
}
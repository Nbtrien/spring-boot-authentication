package com.example.api.service;

import com.example.api.dto.authentication.AuthResponseDto;
import com.example.api.dto.authentication.LoginRequestDto;
import com.example.api.dto.authentication.RegisterRequestDto;

public interface AuthenticationService {
    AuthResponseDto authenticate(LoginRequestDto requestDto);

    AuthResponseDto register(RegisterRequestDto requestDto);

}

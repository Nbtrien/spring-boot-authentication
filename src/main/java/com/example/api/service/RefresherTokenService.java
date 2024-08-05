package com.example.api.service;

import com.example.api.dto.authentication.AuthResponseDto;
import com.example.api.dto.authentication.RefreshTokenRequestDto;
import com.example.api.entity.RefreshToken;

public interface RefresherTokenService {
    RefreshToken createRefreshToken(Long accountId);

    AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto);

    RefreshToken findByToken(String token);

    RefreshToken findByAccountId(Long accountId);

    RefreshToken verifyExpiration(RefreshToken token);

    void deleteByAccountId(Long accountId);
}

package com.example.api.service.impl;

import com.example.api.dto.authentication.AuthResponseDto;
import com.example.api.dto.authentication.RefreshTokenRequestDto;
import com.example.api.entity.Account;
import com.example.api.entity.RefreshToken;
import com.example.api.exception.RefreshTokenException;
import com.example.api.exception.UnauthorizedUserException;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.RefreshTokenRepository;
import com.example.api.security.AccountDetailsService;
import com.example.api.security.JwtTokenUtil;
import com.example.api.security.UserPrincipal;
import com.example.api.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountDetailsService accountDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${security.jwt.refreshExpiration}")
    private Long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(accountRepository.findById(accountId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto) {
        RefreshToken refreshToken = findByToken(requestDto.getToken());
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) > 0) {
            Account account = refreshToken.getAccount();
            UserPrincipal userPrincipal = (UserPrincipal) accountDetailsService.loadUserByUsername(account.getEmail());
            String accessToken = jwtTokenUtil.generateToken(userPrincipal);

            return AuthResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken.getToken())
                    .accountId(userPrincipal.getId())
                    .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.toList())).build();
        } else {
            throw new RefreshTokenException("Refresh token is invalid!");
        }

    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException("refresh token invalid"));
    }

    @Override
    public RefreshToken findByAccountId(Long accountId) {
        return refreshTokenRepository.findByAccount_AccountId(accountId).orElse(null);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Refresh token was expired. Please login again.");
        }
        return refreshToken;
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        refreshTokenRepository.deleteByAccount(accountRepository.findById(accountId).get());
    }

    @Override
    public void deleteByAccount(Account account) {
        RefreshToken refreshToken = refreshTokenRepository.findByAccount(account)
                .orElseThrow(UnauthorizedUserException::new);
        refreshTokenRepository.delete(refreshToken);
    }
}

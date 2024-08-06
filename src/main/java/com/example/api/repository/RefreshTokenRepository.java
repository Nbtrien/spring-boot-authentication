package com.example.api.repository;

import com.example.api.entity.Account;
import com.example.api.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByAccount_AccountId(Long accountId);

    Optional<RefreshToken> findByAccount(Account account);

    void deleteByAccount(Account account);
}

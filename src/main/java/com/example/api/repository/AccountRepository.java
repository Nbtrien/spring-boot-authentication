package com.example.api.repository;

import com.example.api.code.AuthCode;
import com.example.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a INNER JOIN a.roles r ON r.roleName = :role")
    List<Account> findByRole(@Param("role") AuthCode.AuthRole role);

    Optional<Account> findFirstByEmailAndIsDeleteIsFalse(String email);

    Optional<Account> findFirstByUserNameAndIsDeleteIsFalse(String userName);
}

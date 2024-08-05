package com.example.api.security;

import com.example.api.entity.Account;
import com.example.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByEmailAndIsDeleteIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Your email address or password is incorrect."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getCode())));
//        return new UserPrincipal(account.getEmail(), account.getPassword(), authorities, account.getAccountId());
        return UserPrincipal.create(account);
    }
}
package com.example.api.service.impl;

import com.example.api.dto.authentication.AuthResponseDto;
import com.example.api.dto.authentication.LoginRequestDto;
import com.example.api.dto.authentication.RegisterRequestDto;
import com.example.api.entity.Account;
import com.example.api.entity.RefreshToken;
import com.example.api.entity.Role;
import com.example.api.exception.ResourceExistException;
import com.example.api.exception.UnauthorizedUserException;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.RoleRepository;
import com.example.api.security.AccountDetailsService;
import com.example.api.security.JwtTokenUtil;
import com.example.api.security.UserPrincipal;
import com.example.api.service.AuthenticationService;
import com.example.api.service.RefresherTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountDetailsService accountDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefresherTokenService refresherTokenService;

    @Override
    public AuthResponseDto authenticate(LoginRequestDto requestDto) {
        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                                                                                           requestDto.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) accountDetailsService.loadUserByUsername(requestDto.getEmail());

        if (auth.isAuthenticated()) {
            String accessToken = jwtTokenUtil.generateToken(userPrincipal);
            RefreshToken refreshToken = refresherTokenService.createRefreshToken(userPrincipal.getId());

            return AuthResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken.getToken())
                    .accountId(userPrincipal.getId())
                    .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.toList())).build();
        } else {
            throw new UnauthorizedUserException("Can't login to server.");
        }
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (accountRepository.findFirstByEmailAndIsDeleteIsFalse(requestDto.getEmail()).isPresent()) {
            throw new ResourceExistException("This email is already in use. Please use another email.");
        }
        if (accountRepository.findFirstByUserNameAndIsDeleteIsFalse(requestDto.getUserName()).isPresent()) {
            throw new ResourceExistException("This user name is already in use. Please use another user name.");
        }
        Set<Role> roles = requestDto.getRoleIds().stream().map(id -> roleRepository.findById(id).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toSet());

        Account account = Account.builder().userName(requestDto.getUserName()).email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword())).roles(roles).build();

        accountRepository.saveAndFlush(account);
        return authenticate(new LoginRequestDto(requestDto.getEmail(), requestDto.getPassword()));
    }
}

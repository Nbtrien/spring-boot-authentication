package com.example.api.service.impl;

import com.example.api.code.AuthCode;
import com.example.api.dto.account.GetAccountResponseDto;
import com.example.api.dto.account.UpdateAccountRequestDto;
import com.example.api.entity.Account;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.AccountMapper;
import com.example.api.repository.AccountRepository;
import com.example.api.security.SecurityContextUtil;
import com.example.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public GetAccountResponseDto getDetail() {
        Account account = getCurrentAccount();
        return AccountMapper.INSTANCE.toResponseDto(account);
    }

    @Override
    public List<GetAccountResponseDto> getAllUsers() {
        List<Account> accounts = accountRepository.findByRole(AuthCode.AuthRole.USER);
        return accounts.stream().map(AccountMapper.INSTANCE::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<GetAccountResponseDto> getAllAdmins() {
        List<Account> accounts = accountRepository.findByRole(AuthCode.AuthRole.ADMIN);
        return accounts.stream().map(AccountMapper.INSTANCE::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public void update(UpdateAccountRequestDto requestDto) {
        Account account = getCurrentAccount();
        AccountMapper.INSTANCE.updateEntity(requestDto, account);
        accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        account.setDelete(true);
        accountRepository.save(account);
    }

    private Account getCurrentAccount() {
        String email = SecurityContextUtil.currentUser().getUsername();
        Account account = accountRepository.findFirstByEmailAndIsDeleteIsFalse(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        return account;
    }
}

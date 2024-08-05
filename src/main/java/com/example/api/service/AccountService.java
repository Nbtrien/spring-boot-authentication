package com.example.api.service;

import com.example.api.dto.account.GetAccountResponseDto;
import com.example.api.dto.account.UpdateAccountRequestDto;

import java.util.List;

public interface AccountService {
    GetAccountResponseDto getDetail();

    List<GetAccountResponseDto> getAllUsers();

    List<GetAccountResponseDto> getAllAdmins();

    void update(UpdateAccountRequestDto requestDto);

    void deleteById(Long id);
}

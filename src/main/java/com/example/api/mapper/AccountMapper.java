package com.example.api.mapper;

import com.example.api.dto.account.GetAccountResponseDto;
import com.example.api.dto.account.UpdateAccountRequestDto;
import com.example.api.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    GetAccountResponseDto toResponseDto(Account account);

    void updateEntity(UpdateAccountRequestDto source, @MappingTarget Account destinationEntity);
}

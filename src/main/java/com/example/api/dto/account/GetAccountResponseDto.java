package com.example.api.dto.account;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountResponseDto implements Serializable {
    private Long accountId;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
}

package com.example.api.dto.authentication;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto implements Serializable {
    private String accessToken;
    private String refreshToken;
    private Long accountId;
    private List<String> roles;
}

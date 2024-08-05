package com.example.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequestDto {
    @NotBlank(message = "is required field.")
    private String token;
}

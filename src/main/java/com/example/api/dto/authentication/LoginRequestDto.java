package com.example.api.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto implements Serializable {
    @NotBlank(message = "is required field.")
    @Email(message = "is invalid format.")
    private String email;

    @NotBlank(message = "is required field.")
    private String password;
}

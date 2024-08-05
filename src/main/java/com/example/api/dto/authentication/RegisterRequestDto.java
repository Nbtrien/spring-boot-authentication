package com.example.api.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto implements Serializable {
    @NotBlank(message = "is a required field.")
    private String userName;

    @NotBlank(message = "is a required field.")
    @Email(message = "is invalid format.")
    private String email;

    @NotBlank(message = "is a required field.")
    private String password;

    private List<Long> roleIds;
}

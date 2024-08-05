package com.example.api.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountRequestDto implements Serializable {
    @NotBlank(message = "is required field")
    private String userName;

    @NotBlank(message = "is required field")
    private String firstName;

    @NotBlank(message = "is required field")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "is required field")
    private LocalDate dateOfBirth;

    @NotBlank(message = "is required field")
    private String phone;

    @NotBlank(message = "is required field")
    private String address;
}

package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record UserRegister (
        @NotBlank(message = "first name is required")
        @Size(min = 2, max = 50, message = "input value is too long")
        String firstName,

        @NotBlank(message = "last name is required")
        @Size(min = 2, max = 50, message = "input value is too long")
        String lastName,

        @Email
        @NotBlank(message = "email isn't valid")
        String email,

        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$",
                message = "Password must contain [0-9][a-z][A-Z][@#$%^&+=!*]"
        )
        @NotBlank(message = "password is required")
        @Size(min = 8, max = 50, message = "password length invalid")
        String password,

        @NotBlank(message = "role is required")
        @Pattern(regexp = "^(ADMIN|PHARMACIST|admin|pharmacist)$",
                message = "Role must be either ADMIN or PHARMACIST")
        String role
) {}

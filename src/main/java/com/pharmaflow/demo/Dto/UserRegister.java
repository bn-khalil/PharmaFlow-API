package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRegister(
        @NotBlank(message = "first name is required")
        String firstName,
        @NotBlank(message = "last name is required")
        String lastName,
        @Email
        @NotBlank(message = "email isn't valid")
        String email,
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$",
                message = "Password must meet complexity requirements"
        )
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "role is required")
        @Pattern(regexp = "^(ADMIN|USER)$", message = "Role must be either ADMIN or USER")
        String role
) {}

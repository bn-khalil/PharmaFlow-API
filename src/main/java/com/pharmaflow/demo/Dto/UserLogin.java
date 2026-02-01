package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record UserLogin(
        @Email
        @NotBlank(message = "email isn't valid")
        String email,

        @NotBlank(message = "Password is required")
//        @Size(min = 8, max = 50, message = "password length invalid")
        String password
) {}

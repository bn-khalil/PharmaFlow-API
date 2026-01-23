package com.pharmaflow.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EditUser(
        @Size(min = 3, max = 50, message = "input value is too long")
        String firstName,
        @Size(min = 3, max = 50, message = "input value is too long")
        String lastName,
        @Email
        String email
) {
}

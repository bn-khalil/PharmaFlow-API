package com.pharmaflow.demo.Dto;

import com.pharmaflow.demo.Enums.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String role) {
}

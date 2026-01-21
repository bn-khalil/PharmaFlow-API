package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String role,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}

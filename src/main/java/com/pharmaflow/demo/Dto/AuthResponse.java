package com.pharmaflow.demo.Dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String message,
        String email,
        String token) {
}

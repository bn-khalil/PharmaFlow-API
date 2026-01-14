package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,
        String name,
        String description) {
}

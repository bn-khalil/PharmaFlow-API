package com.pharmaflow.demo.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        LocalDateTime timestamp
        ) {}

package com.pharmaflow.demo.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        String path,
        LocalDateTime timestamp
        ) {}

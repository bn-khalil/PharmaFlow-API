package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        String path,
        String errorCode,
        LocalDateTime timestamp
) {}

package com.pharmaflow.demo.Dto;

import lombok.Builder;

@Builder
public record UserLogin(
        String email,
        String password
) {}

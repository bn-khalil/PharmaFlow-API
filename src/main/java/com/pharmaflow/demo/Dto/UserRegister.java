package com.pharmaflow.demo.Dto;

import lombok.Builder;

@Builder
public record UserRegister(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {}

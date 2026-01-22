package com.pharmaflow.demo.Dto;

import lombok.Builder;

@Builder
public record EditUser(
        String firstName,
        String lastName,
        String email
) {
}

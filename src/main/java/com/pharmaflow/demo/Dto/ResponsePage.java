package com.pharmaflow.demo.Dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponsePage<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems,
        int pageSize,
        boolean hasNext,
        boolean hasPrevious) {
}

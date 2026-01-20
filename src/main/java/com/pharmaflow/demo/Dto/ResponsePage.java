package com.pharmaflow.demo.Dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record ResponsePage<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems,
        int pageSize,
        boolean hasNext,
        boolean hasPrevious)
{
    public static <T> ResponsePage<T> fromPage (Page<T> page) {
        return new ResponsePage<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}

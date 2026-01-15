package com.pharmaflow.demo.Exceptions;

public class ResourceDuplicated extends RuntimeException {
    public ResourceDuplicated(String message) {
        super(message);
    }
}

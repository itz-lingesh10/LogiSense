package com.logisense.common.exception;

/**
 * Thrown when a requested resource (e.g. order, user, vehicle) cannot be found (HTTP 404).
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

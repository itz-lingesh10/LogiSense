package com.logisense.common.exception;

/**
 * Thrown when an operation conflicts with current state (e.g. duplicate email, vehicle already assigned) (HTTP 409).
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}

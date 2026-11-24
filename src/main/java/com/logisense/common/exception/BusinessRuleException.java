package com.logisense.common.exception;

/**
 * Thrown when an operation violates domain business rules (e.g. invalid status transition, weight capacity exceeded) (HTTP 422).
 */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}

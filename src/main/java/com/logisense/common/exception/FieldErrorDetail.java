package com.logisense.common.exception;

/**
 * Details of a field-level validation failure.
 *
 * @param field   the name of the invalid field (e.g., "email")
 * @param message the validation error message (e.g., "must not be blank")
 */
public record FieldErrorDetail(
        String field,
        String message
) {
}

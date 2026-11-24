package com.logisense.common.exception;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * Standard uniform error response returned by all REST endpoints.
 *
 * @param timestamp   the timestamp when the error occurred
 * @param status      HTTP status code integer (e.g. 400, 404, 500)
 * @param error       HTTP reason phrase (e.g. "Not Found", "Bad Request")
 * @param message     human-readable error description
 * @param path        the request URI that generated the error
 * @param fieldErrors list of field-level validation errors (empty list if not a validation error)
 */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDetail> fieldErrors
) {
    public ErrorResponse {
        if (fieldErrors == null) {
            fieldErrors = Collections.emptyList();
        }
    }

    /**
     * Convenience constructor for non-validation errors (without field errors).
     */
    public ErrorResponse(Instant timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, Collections.emptyList());
    }
}

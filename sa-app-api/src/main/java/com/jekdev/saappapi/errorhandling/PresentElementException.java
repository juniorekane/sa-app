package com.jekdev.saappapi.errorhandling;

/**
 * Exception thrown when an operation fails due to the presence of an existing element in the application context.
 * <p>
 * This exception is designed to signify conflicts that occur when attempting to add or process an element that already
 * exists in the system, where such duplication is not permitted. It is primarily used to enforce unique constraints or
 * to prevent duplicate records or data entries.
 * <p>
 * When handled globally, such as in a centralized exception handler, this exception can be mapped to return a
 * standardized error response with an appropriate HTTP status code, such as 409 Conflict.
 * <p>
 * This exception facilitates clear communication of the conflict back to the user or client, allowing them to
 * understand and resolve the issue appropriately.
 *
 * @see GlobalExceptionHandler
 */
public class PresentElementException extends RuntimeException {
    public PresentElementException(String message) {
        super(message);
    }
}

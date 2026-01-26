package com.jekdev.com.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there is a conflict due to an element already being present in the application.
 *
 * <p>This exception is typically used to signal that a specific operation cannot proceed because the element in
 * question already exists and violates a constraint or expectation of uniqueness. As a result, it generates a 409
 * Conflict HTTP response status.
 *
 * <p>The exception is associated with Spring's {@code @ResponseStatus} annotation, which automatically maps the
 * exception to an HTTP response with the status {@code HttpStatus.CONFLICT}.
 *
 * <p>For global handling of this exception and to ensure consistent error responses, refer to the {@code
 * GlobalExceptionHandler} class, where it is mapped to produce a standardized error message in a JSON format.
 *
 * @see ResponseStatus
 * @see GlobalExceptionHandler
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class PresentElementException extends RuntimeException {
  public PresentElementException(String message) {
    super(message);
  }
}

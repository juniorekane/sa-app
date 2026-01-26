package com.jekdev.com.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the requested element cannot be found within the application.
 *
 * <p>This exception is typically used to signal that a specific resource or data entity was not found, resulting in a
 * 404 Not Found HTTP response status. It serves as a mechanism to communicate such errors in a standardized manner
 * across the application.
 *
 * <p>The exception is associated with Spring's {@code @ResponseStatus} annotation, which automatically maps the
 * exception to an HTTP response with the status {@code HttpStatus.NOT_FOUND}.
 *
 * <p>For global handling of this exception and to ensure consistent error responses, refer to the {@code
 * GlobalExceptionHandler} class, where it is mapped to produce a standardized error message in a JSON format.
 *
 * @see ResponseStatus
 * @see GlobalExceptionHandler
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException {
  public ElementNotFoundException(String message) {
    super(message);
  }
}

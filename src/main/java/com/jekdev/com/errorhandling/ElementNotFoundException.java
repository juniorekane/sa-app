package com.jekdev.com.errorhandling;


/**
 * Exception thrown when a requested element cannot be found in the application.
 *
 * <p>This exception signals that an attempt to fetch or access a specific element has failed because the element does
 * not exist. It is typically used in scenarios where the application cannot locate the requested data due to its
 * absence. As a runtime exception, it allows developers to handle missing elements contextually in the application.
 *
 * <p>For example, this exception corresponds to cases where an invalid query or operation is attempted for a
 * non-existent element, and it is commonly used in conjunction with a global exception handling mechanism. In the
 * context of a centralized exception handler, such as {@code GlobalExceptionHandler}, this exception can be mapped to
 * return a standardized error response with an HTTP 400 Bad Request status.
 *
 * @see GlobalExceptionHandler
 */
public class ElementNotFoundException extends RuntimeException {
  public ElementNotFoundException(String message) {
    super(message);
  }
}

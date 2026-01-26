package com.jekdev.com.errorhandling;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A centralized exception handler for managing and customizing the responses to application-specific exceptions. This
 * class provides mechanisms to handle specific exceptions globally across the application, ensuring consistent error
 * responses. It uses Spring's @RestControllerAdvice for intercepting and processing exceptions thrown by controllers.
 *
 * <p>The following exception types are handled:
 *
 * <p>- {@code ElementNotFoundException}: Generates a 400 Bad Request response with an error message when the requested
 * element cannot be found in the application.
 *
 * <p>- {@code PresentElementException}: Generates a 400 Bad Request response with an error message when there is a
 * conflict due to the element already being present.
 *
 * <p>Each exception handler returns a {@code ResponseEntity} containing a JSON response body with an error message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@code ElementNotFoundException} thrown within the application. This method captures the exception and
   * constructs a standardized response containing a 400 Bad Request status and a JSON body with an error message.
   *
   * @param ex the {@code ElementNotFoundException} instance containing details about the error
   * @return a {@code ResponseEntity} object with a 400 Bad Request status and a body containing an error message in
   *     JSON format
   */
  @ExceptionHandler(value = ElementNotFoundException.class, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> handleElementNotFoundException(ElementNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
  }

  /**
   * Handles {@code PresentElementException} thrown within the application. This method captures the exception and
   * constructs a standardized response containing a 409-Conflict HTTP status and a JSON body with an error message.
   *
   * @param ex the {@code PresentElementException} instance containing details about the error
   * @return a {@code ResponseEntity} object with a 409-Conflict HTTP status and a body containing an error message in
   *     JSON format
   */
  @ExceptionHandler(value = PresentElementException.class, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, String>> handlePresentElementException(PresentElementException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
  }
}

package com.jekdev.com.errorhandling;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ElementNotFoundException.class, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> handleElementNotFoundException(ElementNotFoundException ex) {
    return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(value = PresentElementException.class, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> handlePresentElementException(PresentElementException ex) {
    return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
  }
}

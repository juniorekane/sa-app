package com.jekdev.saappfrontend.errorhandling;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * Exception thrown when creating a resource that already exists.
 *
 * <p>Contains status metadata used by the frontend error page renderer.
 */
@Getter
public class PresentElementException extends RuntimeException {
  private final String statusText;
  private final HttpStatusCode statusCode;

  /**
   * Creates a new exception instance with backend status context.
   *
   * @param message backend error message
   * @param statusText backend status text
   * @param statusCode backend status code
   */
  public PresentElementException(String message, String statusText, HttpStatusCode statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.statusText = statusText;
  }
}

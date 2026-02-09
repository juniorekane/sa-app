package com.jekdev.saappfrontend.errorhandling;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * Exception thrown when a requested backend element cannot be found.
 *
 * <p>Contains status metadata used by the frontend error page renderer.
 */
@Getter
public class ElementNotFoundException extends RuntimeException {
  private final String statusText;
  private final HttpStatusCode statusCode;

  /**
   * Creates a new exception instance with backend status context.
   *
   * @param message backend error message
   * @param statusText backend status text
   * @param statusCode backend status code
   */
  public ElementNotFoundException(String message, String statusText, HttpStatusCode statusCode) {
    super(message);
    this.statusText = statusText;
    this.statusCode = statusCode;
  }
}

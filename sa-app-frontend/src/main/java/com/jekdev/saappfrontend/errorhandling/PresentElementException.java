package com.jekdev.saappfrontend.errorhandling;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class PresentElementException extends RuntimeException {
  private final String statusText;
  private final HttpStatusCode statusCode;

  public PresentElementException(String message, String statusText, HttpStatusCode statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.statusText = statusText;
  }
}

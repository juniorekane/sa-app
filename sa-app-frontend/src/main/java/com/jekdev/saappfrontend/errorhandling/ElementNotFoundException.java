package com.jekdev.saappfrontend.errorhandling;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ElementNotFoundException extends RuntimeException {
  private final String statusText;
  private final HttpStatusCode statusCode;

  public ElementNotFoundException(String message, String statusText, HttpStatusCode statusCode) {
    super(message);
    this.statusText = statusText;
    this.statusCode = statusCode;
  }
}

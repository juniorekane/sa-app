package com.jekdev.com.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PresentElementException extends RuntimeException {
  public PresentElementException(String message) {
    super(message);
  }
}

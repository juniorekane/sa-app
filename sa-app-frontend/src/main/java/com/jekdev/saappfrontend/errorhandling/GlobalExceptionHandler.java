package com.jekdev.saappfrontend.errorhandling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ElementNotFoundException.class)
  public String handleElementNotFoundException(ElementNotFoundException exception, Model model) {
    model.addAttribute("statusCode", exception.getStatusCode().value());
    model.addAttribute("statusText", exception.getStatusText());
    model.addAttribute("errorMessage", exception.getMessage());
    return "error";
  }

  @ExceptionHandler(PresentElementException.class)
  public String handlePresentElementException(PresentElementException exception, Model model) {
    model.addAttribute("statusCode", exception.getStatusCode().value());
    model.addAttribute("statusText", exception.getStatusText());
    model.addAttribute("errorMessage", exception.getMessage());
    return "error";
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleServerError(HttpServerErrorException ex, Model model) {
    model.addAttribute("statusCode", ex.getStatusCode().value());
    model.addAttribute("statusText", ex.getStatusText());
    model.addAttribute("errorMessage", ex.getMessage());
    return "error";
  }

  @ExceptionHandler(Exception.class)
  public String handleAny(Exception ex, Model model) {
    model.addAttribute("errorMessage", ex.getMessage());
    return "error";
  }
}

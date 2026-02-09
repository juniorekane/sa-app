package com.jekdev.saappfrontend.errorhandling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Centralized exception mapping for frontend MVC controllers.
 *
 * <p>Transforms known exceptions into a unified error page model.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles backend 4xx-like "not found" style errors mapped to {@link ElementNotFoundException}.
   *
   * @param exception thrown exception
   * @param model Spring MVC model
   * @return error template name
   */
  @ExceptionHandler(ElementNotFoundException.class)
  public String handleElementNotFoundException(ElementNotFoundException exception, Model model) {
    model.addAttribute("statusCode", exception.getStatusCode().value());
    model.addAttribute("statusText", exception.getStatusText());
    model.addAttribute("errorMessage", exception.getMessage());
    return "error";
  }

  /**
   * Handles backend conflict-style errors mapped to {@link PresentElementException}.
   *
   * @param exception thrown exception
   * @param model Spring MVC model
   * @return error template name
   */
  @ExceptionHandler(PresentElementException.class)
  public String handlePresentElementException(PresentElementException exception, Model model) {
    model.addAttribute("statusCode", exception.getStatusCode().value());
    model.addAttribute("statusText", exception.getStatusText());
    model.addAttribute("errorMessage", exception.getMessage());
    return "error";
  }

  /**
   * Handles backend 5xx server errors.
   *
   * @param ex thrown server exception
   * @param model Spring MVC model
   * @return error template name
   */
  @ExceptionHandler(HttpServerErrorException.class)
  public String handleServerError(HttpServerErrorException ex, Model model) {
    model.addAttribute("statusCode", ex.getStatusCode().value());
    model.addAttribute("statusText", ex.getStatusText());
    model.addAttribute("errorMessage", ex.getMessage());
    return "error";
  }

  /**
   * Fallback handler for any uncaught exception.
   *
   * @param ex thrown exception
   * @param model Spring MVC model
   * @return error template name
   */
  @ExceptionHandler(Exception.class)
  public String handleAny(Exception ex, Model model) {
    model.addAttribute("errorMessage", ex.getMessage());
    return "error";
  }
}

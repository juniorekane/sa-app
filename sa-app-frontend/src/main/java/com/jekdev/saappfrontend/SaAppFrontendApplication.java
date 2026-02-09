package com.jekdev.saappfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the frontend Spring Boot application.
 *
 * <p>This class bootstraps the UI module and starts the embedded web server for the Thymeleaf-based frontend.
 */
@SpringBootApplication
public class SaAppFrontendApplication {

  /**
   * Starts the Spring Boot frontend application.
   *
   * @param args standard JVM startup arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(SaAppFrontendApplication.class);
  }
}

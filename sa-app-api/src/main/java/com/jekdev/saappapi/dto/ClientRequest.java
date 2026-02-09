package com.jekdev.saappapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a request containing client-specific information, primarily the client's email address. This class is used
 * to encapsulate the email details of a client when creating or processing requests within the application.
 *
 * <p>The {@code ClientRequest} class is designed to ensure validation of the provided email address. It includes
 * constraints to verify that the email is not blank and adheres to a valid email format. This makes the class reliable
 * for scenarios requiring validation and later handling of client email data.
 *
 * <p>This class leverages Lombok annotations to reduce boilerplate code for constructors, getters, and setters.
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ClientRequest {

  @NotBlank(message = "Email address is required")
  @Email(message = "Invalid email address format")
  private String email;
}

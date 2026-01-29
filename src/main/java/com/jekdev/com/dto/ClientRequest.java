package com.jekdev.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a request containing client-specific information to be processed. This class is primarily used for
 * transferring client data, such as email, during interaction with various layers of the application or external
 * systems.
 *
 * <p>The {@code ClientRequest} class is a simple DTO (Data Transfer Object) with a single property, {@code email}, that
 * is intended to capture the email address of the client. It leverages Lombok annotations for reducing boilerplate
 * getter, setter, and constructor code.
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ClientRequest {

  @NotBlank(message = "Email address is required")
  @Email(message = "Invalid email address format")
  private String email;
}

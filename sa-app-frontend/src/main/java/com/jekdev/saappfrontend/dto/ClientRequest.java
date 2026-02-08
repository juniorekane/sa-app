package com.jekdev.saappfrontend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating or referencing a client via email address.
 *
 * <p>Validation ensures the field is present and has a valid email format.
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ClientRequest {
  @NotBlank(message = "Email address is required")
  @Email(message = "Invalid email address format")
  private String email;
}

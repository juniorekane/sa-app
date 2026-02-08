package com.jekdev.saappfrontend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ClientRequest {
  @NotBlank(message = "Email address is required")
  @Email(message = "Invalid email address format")
  private String email;
}

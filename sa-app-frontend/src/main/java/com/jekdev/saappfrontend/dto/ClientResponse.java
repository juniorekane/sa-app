package com.jekdev.saappfrontend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a client within the system, encapsulating their unique identifier, email address, and an optional list of
 * emotional data summaries associated with them.
 *
 * <p>This class serves as a Data Transfer Object (DTO) to carry client-specific information between layers or systems
 * in the application. It is typically used in scenarios where client data, along with their related emotional
 * summaries, needs to be transmitted or processed. The {@code ClientResponse} class includes key attributes: - {@code
 * id}: A non-null unique identifier for the client. - {@code email}: A non-null email address associated with the
 * client. - {@code emotions}: An optional list of {@code EmotionSummary} instances representing emotions linked to the
 * client.
 *
 * <p>This class leverages Lombok annotations to reduce boilerplate code by auto-generating constructors, getters, and
 * setters. Using this class ensures a consistent and type-safe way of handling client response data across the
 * application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientResponse {
  private Long id;
  private String email;
  private List<EmotionSummary> emotions;
}

package com.jekdev.saappfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a response encapsulating information about an emotion associated with a client. This class is utilized for
 * transferring emotion-related data as part of the application's data flow.
 *
 * <p>The {@code EmotionResponse} class includes: - {@code id}: A unique identifier for the emotion entry. - {@code
 * text}: The textual representation or description of the emotion. - {@code type}: The type of emotion, represented by
 * the {@code String} enum. - {@code client}: A {@code ClientResponse} instance that provides details about the client
 * related to the emotion.
 *
 * <p>This class leverages Lombok annotations to generate boilerplate code such as constructors, getters, and setters,
 * enabling a streamlined approach to handling emotion-response data in the application. It serves as a Data Transfer
 * Object (DTO) for communication between various layers or systems.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmotionResponse {

  private Long id;

  private String text;

  private String type;

  private ClientResponse client;
}

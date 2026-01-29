package com.jekdev.com.dto;

import com.jekdev.com.base.EmotionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to evaluate or process a specific emotional context provided in textual form. This class is
 * intended to encapsulate the necessary details required to identify and categorize the emotion contained within a
 * given piece of text.
 *
 * <p>The {@code EmotionRequest} class includes the following components: - {@code text}: The text input for which the
 * emotional context needs to be processed or evaluated. - {@code type}: The expected or predefined type of emotion
 * associated with the input text. This is represented by the {@code EmotionType} enum. - {@code clientRequest}:
 * Information about the client initiating the request, provided via a {@code ClientRequest} instance, which assists in
 * contextualizing the emotion evaluation.
 *
 * <p>This class leverages Lombok annotations to reduce boilerplate code such as constructors, getters, and setters. It
 * is typically used as a Data Transfer Object (DTO) within the application to carry emotion analysis requests between
 * layers or systems.
 */
@AllArgsConstructor
@Setter
@Getter
public class EmotionRequest {
  @NotBlank(message = "The provided text should not be blank")
  private String text;

  @Nullable private EmotionType type;
  private ClientRequest client;
}

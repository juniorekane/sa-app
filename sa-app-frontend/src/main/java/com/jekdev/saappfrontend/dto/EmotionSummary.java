package com.jekdev.saappfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a summary of an emotion, encapsulating its unique identifier, descriptive text, and associated type. This
 * class is used to provide a compact representation of an emotion that can be linked to entities such as clients or
 * events within the application.
 *
 * <p>The {@code EmotionSummary} class includes the following attributes: - {@code id}: A unique identifier for the
 * emotion entry. - {@code text}: A textual description or representation of the emotion. - {@code type}: The type of
 * emotion, categorized using the {@code EmotionType} enumeration.
 *
 * <p>This class is typically used in scenarios requiring a lightweight aggregation of emotion data, such as summarizing
 * emotions linked to a specific client or context. It facilitates efficient data encapsulation and transfer across
 * different layers or components of the application.
 *
 * <p>This class leverages Lombok annotations to reduce boilerplate code for constructors and getters, making it easier
 * to manage and maintain.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmotionSummary {

  private Long id;

  private String text;

  private String type;
}

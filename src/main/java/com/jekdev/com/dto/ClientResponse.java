package com.jekdev.com.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a response containing client-specific information that has been processed or retrieved. This class is
 * primarily used for transferring client data, such as a unique identifier and email address, between various layers of
 * the application or as part of an API response.
 *
 * <p>The {@code ClientResponse} class encapsulates the client's identifier and email details, making it suitable for
 * scenarios where client information needs to be conveyed after processing a request or retrieving data from storage.
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientResponse {
  @NonNull private Long id;
  @NonNull private String email;
  @Nullable private List<EmotionSummary> emotions;
}

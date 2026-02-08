package com.jekdev.saappfrontend.service;

import com.jekdev.saappfrontend.dto.ClientRequest;
import com.jekdev.saappfrontend.dto.ClientResponse;
import com.jekdev.saappfrontend.dto.EmotionRequest;
import com.jekdev.saappfrontend.dto.EmotionResponse;
import com.jekdev.saappfrontend.errorhandling.ElementNotFoundException;
import com.jekdev.saappfrontend.errorhandling.PresentElementException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class SentimentApiService {

  private final RestClient client;

  public HttpStatusCode createUser(ClientRequest request) {
    try {

      return client.post().uri("/api/client/create").body(request).retrieve().toBodilessEntity().getStatusCode();

    } catch (HttpClientErrorException exception) {

      String msg = exception.getResponseBodyAsString();

      if (msg.isBlank()) {
        msg = exception.getMessage();
      }

      throw new PresentElementException(msg, exception.getStatusText(), exception.getStatusCode());
    }
  }

  public List<ClientResponse> getAllClient() {
    try {

      return client
          .get()
          .uri("/api/client/find_all")
          .retrieve()
          .body(new ParameterizedTypeReference<List<ClientResponse>>() {});

    } catch (HttpClientErrorException e) {

      String message = e.getResponseBodyAsString();

      if (message.isBlank()) {
        message = e.getMessage();
      }

      throw new ElementNotFoundException(message, e.getStatusText(), e.getStatusCode());
    }
  }

  public ClientResponse findSingleClient(Long id) {

    try {

      return client
          .get()
          .uri("/api/client/search/" + id)
          .retrieve()
          .body(new ParameterizedTypeReference<ClientResponse>() {});

    } catch (HttpClientErrorException e) {

      String message = e.getResponseBodyAsString();

      if (message.isBlank()) {
        message = e.getMessage();
      }

      throw new ElementNotFoundException(message, e.getStatusText(), e.getStatusCode());
    }
  }

  public HttpStatusCode createEmotion(EmotionRequest request) {
    try {

      return client.post().uri("/api/emotions/create").body(request).retrieve().toBodilessEntity().getStatusCode();

    } catch (HttpClientErrorException exception) {

      String message = exception.getResponseBodyAsString();

      if (message.isBlank()) {
        message = exception.getMessage();
      }

      throw new PresentElementException(message, exception.getStatusText(), exception.getStatusCode());
    }
  }

  public List<EmotionResponse> getAllEmotions() {
    try {

      return client
          .get()
          .uri("/api/emotions/all")
          .retrieve()
          .body(new ParameterizedTypeReference<List<EmotionResponse>>() {});

    } catch (HttpClientErrorException exception) {

      String message = exception.getResponseBodyAsString();

      if (message.isBlank()) {
        message = exception.getMessage();
      }

      throw new ElementNotFoundException(message, exception.getStatusText(), exception.getStatusCode());
    }
  }

  public HttpStatusCode deleteEmotion(Long id) {
    try {

      return client
          .delete()
          .uri("/api/emotions/delete/" + id)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .retrieve()
          .toBodilessEntity()
          .getStatusCode();

    } catch (HttpClientErrorException exception) {

      String message = exception.getResponseBodyAsString();

      if (message.isBlank()) {
        message = exception.getMessage();
      }

      throw new ElementNotFoundException(message, exception.getStatusText(), exception.getStatusCode());
    }
  }
}

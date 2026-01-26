package com.jekdev.com.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.com.dto.EmotionRequest;
import com.jekdev.com.dto.EmotionResponse;
import com.jekdev.com.entities.Emotion;
import com.jekdev.com.service.EmotionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller providing endpoints for managing {@link Emotion} entities. This controller maps HTTP requests and
 * delegates actions to the {@link EmotionService}. It supports create, retrieve, and delete operations for emotions.
 */
@RestController
@RequestMapping(value = EmotionController.BASE_PATH, produces = APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Emotion Controller", description = "RestController that provides many endpoints to handle emotion")
public class EmotionController {

  /**
   * The base path used for mapping HTTP requests to the endpoints of the {@code EmotionController}. This constant is
   * referenced in the {@code @RequestMapping} annotation to define the root URL for all endpoints within the {@code
   * EmotionController}.
   */
  public static final String BASE_PATH = "emotions";

  /**
   * A string constant representing the path segment for creating a new resource. Used within controller endpoint
   * mappings to define the relative URL for create operations.
   *
   * <p>In the context of the {@code EmotionController}, this constant specifies the path segment for the HTTP POST
   * endpoint that handles the creation of an {@code Emotion} entity. The full path is constructed using the {@code
   * BASE_PATH} of the controller and this constant, forming the endpoint URL for create operations.
   */
  public static final String CREATE_PATH = "create";

  /**
   * A string constant representing the path segment for deleting an existing resource. Used within controller endpoint
   * mappings to define the relative URL for delete operations.
   *
   * <p>In the context of the {@code EmotionController}, this constant specifies the path segment for the HTTP DELETE
   * endpoint that handles the deletion of an {@code Emotion} entity identified by its unique identifier.
   *
   * <p>The full path is constructed using the {@code BASE_PATH} of the controller and this constant, forming the
   * endpoint URL for delete operations. Example usage: DELETE request to the constructed URL will remove the
   * corresponding resource identified by the {id} path variable.
   */
  public static final String DELETE_PATH = "delete/{id}";

  public static final String ALL_EMOTION_PATH = "all";

  private final EmotionService emotionService;

  /**
   * Handles the creation of a new emotion based on the provided request data. This method processes HTTP POST requests
   * sent to the specified endpoint, validates the incoming {@link EmotionRequest}, and passes it to the underlying
   * service for further processing. If successful, it returns an HTTP status of 201 (CREATED).
   *
   * @param emotion the {@link EmotionRequest} containing the information needed to create a new emotion; must include
   *     valid details such as text and, optionally, type and client context
   * @return a {@link ResponseEntity} with no content and an HTTP status of 201 (CREATED) to indicate successful
   *     creation
   */
  @PostMapping(value = CREATE_PATH, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createEmotion(
      @Parameter(name = "emotion", description = "the emotion to be created") @Valid @RequestBody
          EmotionRequest emotion) {
    emotionService.createEmotion(emotion);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Retrieves a list of all emotions stored in the system. This method handles HTTP GET requests and returns a JSON
   * response containing a collection of all emotions. Each emotion is represented as an {@code EmotionResponse} object
   * and includes relevant details such as its unique identifier, text, type, and associated client information.
   *
   * @return a {@link ResponseEntity} containing a JSON-formatted string representation of all emotions, along with an
   *     HTTP status of 200 (OK)
   */
  @GetMapping(value = ALL_EMOTION_PATH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getAllEmotions() {
    List<EmotionResponse> emotionResponseList = emotionService.findAllEmotion();
    return ResponseEntity.ok().body(emotionResponseList.toString());
  }

  /**
   * Deletes an {@link Emotion} entity based on its unique identifier. This method processes an HTTP DELETE request and
   * ensures that the specified emotion is removed from the database if it exists. If the emotion cannot be found, an
   * appropriate exception is thrown.
   *
   * @param id the unique identifier of the {@link Emotion} to be deleted; must not be null
   * @return a {@link ResponseEntity} with no content if the emotion is successfully deleted
   */
  @DeleteMapping(value = DELETE_PATH, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> deleteEmotion(@Valid @PathVariable Long id) {
    emotionService.deleteEmotion(id);
    return ResponseEntity.noContent().build();
  }
}

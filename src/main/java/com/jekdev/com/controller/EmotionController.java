package com.jekdev.com.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.com.entities.Emotion;
import com.jekdev.com.service.EmotionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

  private final EmotionService emotionService;

  /**
   * Handles the creation of a new emotion entity in the system. This method processes an HTTP POST request containing a
   * JSON payload representing the emotion and persists it into the database if it does not already exist.
   *
   * @param emotion the emotion entity to be created; must not be null and must contain a valid text, type, and
   *     associated client
   */
  @PostMapping(value = CREATE_PATH, consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createEmotion(
      @Parameter(name = "emotion", description = "the emotion to be created") @RequestBody Emotion emotion) {
    emotionService.createEmotion(emotion);
  }

  /**
   * Retrieves a list of all {@link Emotion} entities available in the system. This method processes an HTTP GET request
   * and responds with the complete collection of emotions currently stored in the database.
   *
   * @return a list of {@code Emotion} entities, representing all emotions in the system
   */
  @GetMapping
  @ResponseStatus(value = HttpStatus.OK, reason = "If a list of emotions exists and can be successfully returned")
  public List<Emotion> getAllEmotions() {
    return emotionService.findAllEmotion();
  }

  /**
   * Deletes an emotion entity identified by its unique identifier. This method handles HTTP DELETE requests and removes
   * the specified emotion from the system using the provided identifier.
   *
   * @param id the unique identifier of the emotion to be deleted; must not be null
   */
  @DeleteMapping(value = DELETE_PATH)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteEmotion(@PathVariable Long id) {
    emotionService.deleteEmotion(id);
  }
}

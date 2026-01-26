package com.jekdev.com.service;

import com.jekdev.com.dto.EmotionRequest;
import com.jekdev.com.dto.EmotionResponse;
import com.jekdev.com.entities.Client;
import com.jekdev.com.entities.Emotion;
import com.jekdev.com.mapper.EmotionMapper;
import com.jekdev.com.repositories.EmotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Emotion} entities. Provides methods for creating, retrieving, and
 * deleting emotions, while also enabling mapping between request, entity, and response representations. Utilizes {@link
 * ClientService}, {@link EmotionMapper}, and {@link EmotionRepository} to handle emotion data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmotionService {

  private final ClientService clientService;

  private final EmotionMapper emotionMapper;

  private final EmotionRepository emotionRepository;

  /**
   * Creates a new {@link Emotion} entity or retrieves an existing one. This method processes the provided {@link
   * EmotionRequest}, mapping it to an {@link Emotion} entity, associates it with a corresponding {@link Client}, and
   * then either saves the new entity or logs a debug message if the emotion already exists.
   *
   * @param emotionRequest the {@link EmotionRequest} containing the details for the emotion to be created; must not be
   *     null
   */
  public void createEmotion(EmotionRequest emotionRequest) {
    Emotion emotion = emotionMapper.mapEmotionRequestToEntity(emotionRequest);
    Client client = clientService.readOrCreateClient(emotion.getClient());
    emotion.setClient(client);
    if (emotionRepository.findByText(emotion.getText()).isPresent()) {
      log.debug("Emotion Already exists");
    } else {
      emotionRepository.save(emotion);
      log.info("Emotion saved with id: {}", emotion.getId());
    }
  }

  /**
   * Retrieves a list of all emotion records from the database, maps each {@link Emotion} entity to a {@link
   * EmotionResponse} object, and returns the resulting list. Each {@link EmotionResponse} object contains the relevant
   * details about an emotion, such as its unique identifier, text, type, and associated client information.
   *
   * @return a {@link List} of {@link EmotionResponse} objects representing all emotion records stored in the database
   */
  public List<EmotionResponse> findAllEmotion() {
    return emotionRepository.findAll().stream().map(emotionMapper::mapEmotionEntityToResponse).toList();
  }

  /**
   * Deletes an {@link Emotion} entity from the database using its unique identifier. This method ensures the removal of
   * the specified emotion record if it exists in the repository.
   *
   * @param id the unique identifier of the {@link Emotion} to be deleted; must not be null
   */
  public void deleteEmotion(Long id) {
    emotionRepository.deleteById(id);
  }
}

package com.jekdev.com.service;

import com.jekdev.com.dto.EmotionRequest;
import com.jekdev.com.dto.EmotionResponse;
import com.jekdev.com.entities.Client;
import com.jekdev.com.entities.Emotion;
import com.jekdev.com.errorhandling.ElementNotFoundException;
import com.jekdev.com.errorhandling.PresentElementException;
import com.jekdev.com.mapper.AppMapper;
import com.jekdev.com.repositories.EmotionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Emotion} entities. Provides methods for creating, retrieving, and
 * deleting emotions in the system. This class includes logic for handling relationships between emotions and clients,
 * ensuring consistency and associations are properly maintained. It relies on other components, such as {@link
 * ClientService}, {@link AppMapper}, and {@link EmotionRepository}, to process and persist data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmotionService {

  private final ClientService clientService;

  private final AppMapper appMapper;

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
    Emotion emotion = appMapper.mapEmotionRequestToEntity(emotionRequest);
    Client client = clientService.readOrCreateClient(emotion.getClient());
    emotion.setClient(client);
    if (emotionRepository.findByText(emotion.getText()).isPresent()) {
      log.debug("Emotion Already exists");
      throw new PresentElementException("Emotion already exists. Please use a different text.");
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

    log.info("Fetching all emotions");
    List<Emotion> emotionList = emotionRepository.findAll();

    if (emotionList.isEmpty()) {
      throw new ElementNotFoundException("No emotions found, please create some emotions first.");
    }
    return emotionList.stream().map(appMapper::mapEmotionEntityToResponse).toList();
  }

  /**
   * Deletes an {@link Emotion} entity from the database using its unique identifier. This method ensures the removal of
   * the specified emotion record if it exists in the repository.
   *
   * @param id the unique identifier of the {@link Emotion} to be deleted; must not be null
   */
  @Transactional
  public void deleteEmotion(Long id) {
    emotionRepository
        .findById(id)
        .orElseThrow(() -> new ElementNotFoundException("Emotion with id " + id + " not found."));
    emotionRepository.deleteById(id);
  }
}

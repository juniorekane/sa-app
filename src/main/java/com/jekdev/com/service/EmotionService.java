package com.jekdev.com.service;

import com.jekdev.com.entities.Client;
import com.jekdev.com.entities.Emotion;
import com.jekdev.com.repositories.EmotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Emotion} entities. Provides functionality for creating and managing
 * emotion records in the database.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmotionService {

  private final ClientService clientService;

  private final EmotionRepository emotionRepository;

  /**
   * Persists a new {@link Emotion} entity into the database if it does not already exist. If an emotion with the same
   * text already exists in the repository, this method logs a debug message and does not save the new emotion.
   * Otherwise, the emotion is saved and an informational log entry is recorded with the ID of the saved emotion.
   *
   * @param emotion the emotion entity to be created; must not be null and
   */
  public void createEmotion(Emotion emotion) {
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
   * Retrieves all {@link Emotion} entities stored in the database. This method fetches and returns a complete list of
   * emotions, enabling access to all currently persisted emotion records.
   *
   * @return a {@link List} of {@code Emotion} entities representing all stored emotions
   */
  public List<Emotion> findAllEmotion() {
    return emotionRepository.findAll();
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

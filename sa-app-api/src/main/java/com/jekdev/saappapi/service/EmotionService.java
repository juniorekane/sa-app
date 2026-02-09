package com.jekdev.saappapi.service;

import com.jekdev.saappapi.dto.EmotionRequest;
import com.jekdev.saappapi.dto.EmotionResponse;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.entities.Emotion;
import com.jekdev.saappapi.errorhandling.ElementNotFoundException;
import com.jekdev.saappapi.errorhandling.PresentElementException;
import com.jekdev.saappapi.mapper.AppMapper;
import com.jekdev.saappapi.repositories.EmotionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * EmotionService is a service layer component responsible for managing business logic and operations related to
 * emotions within the application. It serves as an intermediary between the controller and repository layers, enabling
 * the creation, retrieval, and deletion of emotion entities.
 * <p>
 * This class leverages the {@link ClientService} to handle client-related operations and {@link AppMapper} for mapping
 * entity and DTO objects. It also interacts directly with the {@link EmotionRepository} to perform CRUD operations
 * related to emotions.
 * <p>
 * Key functionalities include: - Creating a new emotion or ensuring that duplicate emotions are not stored in the
 * system. - Fetching a list of all stored emotions, mapping them from entities to response objects. - Deleting a
 * specific emotion based on its unique identifier.
 * <p>
 * Logging is extensively used to provide insights during runtime, such as for successful operations, debugging, or when
 * throwing exceptions. This improves traceability and aids in application maintenance.
 * <p>
 * Transactions are applied to ensure consistency and integrity of database operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmotionService {

    private final ClientService clientService;

    private final AppMapper appMapper;

    private final EmotionRepository emotionRepository;
    private final SentimentAnalysisService sentimentAnalysisService;

    /**
     * Creates a new {@link Emotion} entity or retrieves an existing one. This method processes the provided
     * {@link EmotionRequest}, mapping it to an {@link Emotion} entity, associates it with a corresponding
     * {@link Client}, and then either saves the new entity or logs a debug message if the emotion already exists.
     *
     * @param emotionRequest
     *            the {@link EmotionRequest} containing the details for the emotion to be created; must not be null
     */
    public void createEmotion(EmotionRequest emotionRequest) {
        Emotion emotion = appMapper.mapEmotionRequestToEntity(emotionRequest);
        Client client = clientService.readOrCreateClient(emotion.getClient());
        emotion.setClient(client);

        if (emotionRepository.findByText(emotion.getText()).isPresent()) {
            log.debug("Emotion Already exists");
            throw new PresentElementException("Emotion already exists. Please use a different text.");
        } else {
            var sentiment = sentimentAnalysisService.analyze(emotion.getText());
            emotion.setType(sentiment.label());
            emotion.setScore(sentiment.score());
            emotionRepository.save(emotion);
            log.info("Emotion saved with id: {} label: {} score: {}", emotion.getId(), emotion.getType(),
                    emotion.getScore());
        }
    }

    /**
     * Retrieves a list of all emotion records from the database, maps each {@link Emotion} entity to a
     * {@link EmotionResponse} object, and returns the resulting list. Each {@link EmotionResponse} object contains the
     * relevant details about an emotion, such as its unique identifier, text, type, and associated client information.
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
     * Deletes an {@link Emotion} entity from the database using its unique identifier. This method ensures the removal
     * of the specified emotion record if it exists in the repository.
     *
     * @param id
     *            the unique identifier of the {@link Emotion} to be deleted; must not be null
     */
    public void deleteEmotion(Long id) {
        emotionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Emotion with id " + id + " not found."));
        emotionRepository.deleteById(id);
    }
}

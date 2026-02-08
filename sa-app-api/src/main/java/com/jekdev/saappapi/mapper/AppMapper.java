package com.jekdev.saappapi.mapper;

import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.dto.EmotionRequest;
import com.jekdev.saappapi.dto.EmotionResponse;
import com.jekdev.saappapi.dto.EmotionSummary;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.entities.Emotion;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * A utility class responsible for mapping between domain entities and Data Transfer Objects (DTOs) related to clients
 * and emotions within the application.
 * <p>
 * The {@code AppMapper} class provides various methods to facilitate the transformation of objects between different
 * layers of the application, enabling effective communication and data handling. Specifically, it includes:
 * <p>
 * - Mapping a {@code ClientRequest} DTO to a {@code Client} entity. - Mapping a {@code Client} entity to a {@code
 * ClientResponse} DTO. - Mapping an {@code EmotionRequest} DTO to an {@code Emotion} entity. - Mapping an {@code
 * Emotion} entity to an {@code EmotionResponse} DTO. - Mapping an {@code Emotion} entity to an {@code EmotionSummary}
 * for concise representation.
 * <p>
 * These conversion methods help ensure separation of concerns and simplify data handling throughout different
 * components of the application, such as controllers, services, and persistence layers.
 * <p>
 * Annotated with {@code @Component}, this class is managed as a Spring bean, making it available for dependency
 * injection wherever required.
 */
@Component
public class AppMapper {

    /**
     * Maps a {@link ClientRequest} object to a {@link Client} entity.
     * <p>
     * This method transfers data from the {@code ClientRequest} DTO to a new instance of the {@code Client} entity.
     * Specifically, it sets the {@code email} field of the {@code Client} entity based on the corresponding value in
     * the {@code ClientRequest}.
     *
     * @param clientRequest
     *            the {@link ClientRequest} object containing client-specific data; must not be null
     *
     * @return a new {@link Client} entity with data mapped from the provided {@link ClientRequest}
     */
    public Client mapClientRequestToEntity(ClientRequest clientRequest) {
        Client client = new Client();
        client.setEmail(clientRequest.getEmail());
        return client;
    }

    /**
     * Maps a {@link Client} entity to a {@link ClientResponse} object.
     * <p>
     * This method converts the data from a {@code Client} entity into a {@code ClientResponse} object, which is used
     * for transferring client-specific information, such as the client's ID and email address. If the client entity
     * contains a list of emotions, they are mapped to {@link EmotionSummary} objects and included in the response.
     *
     * @param client
     *            the {@link Client} entity to be mapped; must not be null
     *
     * @return a {@link ClientResponse} object containing the mapped client information
     */
    public ClientResponse mapClientEntityToClientResponse(Client client) {
        if (client.getEmotions().isEmpty()) {
            return new ClientResponse(client.getId(), client.getEmail());
        }

        List<EmotionSummary> emotions = client.getEmotions().stream().map(this::mapEmotionToSummary).toList();

        return new ClientResponse(client.getId(), client.getEmail(), emotions);
    }

    /**
     * Maps an {@link Emotion} entity to an {@link EmotionSummary} object.
     * <p>
     * This method converts the data from an {@code Emotion} entity into an {@code EmotionSummary} object, which is a
     * lightweight representation containing only the essential details of the emotion, namely its ID, text, and type.
     *
     * @param emotion
     *            the {@link Emotion} entity to be mapped; must not be null
     *
     * @return an {@link EmotionSummary} object containing the mapped emotion details
     */
    public EmotionSummary mapEmotionToSummary(Emotion emotion) {
        return new EmotionSummary(emotion.getId(), emotion.getText(), emotion.getType(), emotion.getScore());
    }

    /**
     * Maps an {@link EmotionRequest} object to an {@link Emotion} entity.
     * <p>
     * This method transfers the data from an {@code EmotionRequest} DTO to a new {@code Emotion} entity. It maps key
     * attributes, including the text and associated client. The sentiment type is resolved later via an external
     * provider in the service layer. The client information is mapped using the {@code mapClientRequestToEntity}
     * method.
     *
     * @param emotionRequest
     *            the {@link EmotionRequest} object containing the emotion details to be mapped; must not be null
     *
     * @return a new {@link Emotion} entity populated with data from the provided {@link EmotionRequest}
     */
    public Emotion mapEmotionRequestToEntity(EmotionRequest emotionRequest) {
        Emotion emotion = new Emotion();
        emotion.setText(emotionRequest.getText());
        Client client = mapClientRequestToEntity(emotionRequest.getClient());
        emotion.setClient(client);
        return emotion;
    }

    /**
     * Maps an {@link Emotion} entity to an {@link EmotionResponse} object.
     * <p>
     * This method converts the data from an {@code Emotion} entity into an {@code EmotionResponse} object, which is
     * used to encapsulate and transfer emotion-related information. The method extracts relevant fields such as the
     * emotion's ID, text, type, and associated client information (mapped into a {@link ClientResponse} object).
     *
     * @param emotion
     *            the {@link Emotion} entity to be mapped; must not be null
     *
     * @return an {@link EmotionResponse} object containing the mapped emotion details and client information
     */
    public EmotionResponse mapEmotionEntityToResponse(Emotion emotion) {
        ClientResponse clientResponse = new ClientResponse(emotion.getClient().getId(), emotion.getClient().getEmail());
        return new EmotionResponse(emotion.getId(), emotion.getText(), emotion.getType(), emotion.getScore(), clientResponse);
    }
}

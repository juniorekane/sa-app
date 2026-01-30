package com.jekdev.mapper;

import com.jekdev.saappapi.base.EmotionType;
import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.dto.EmotionRequest;
import com.jekdev.saappapi.dto.EmotionResponse;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.entities.Emotion;
import com.jekdev.saappapi.mapper.AppMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppMapperTest {

    private AppMapper mapper;

    private ClientRequest mockClientRequest;

    private Client mockClient;

    private EmotionRequest mockEmotionRequest;

    private Emotion mockEmotion;

    @BeforeEach
    void setUp() {
        mapper = new AppMapper();

        mockClientRequest = new ClientRequest();
        mockClientRequest.setEmail("test@mail.local");

        mockClient = new Client();
        mockClient.setEmail(mockClientRequest.getEmail());
        mockClient.setId(1L);

        mockEmotionRequest = new EmotionRequest("test", EmotionType.BAD, mockClientRequest);

        mockEmotion = new Emotion();
        mockEmotion.setText(mockEmotionRequest.getText());
        mockEmotion.setType(mockEmotionRequest.getType());
        mockEmotion.setClient(mockClient);
    }

    @Test
    void mapClientRequestToEntitySuccess() {
        // Execute test
        Client client = mapper.mapClientRequestToEntity(mockClientRequest);

        // Verify the results
        Assertions.assertEquals(mockClientRequest.getEmail(), client.getEmail());
    }

    @Test
    void mapClientRequestToEntityNullEmailFailure() {
        // Prepare test data
        mockClientRequest.setEmail(null);

        // Execute test
        NullPointerException ex = Assertions.assertThrows(NullPointerException.class,
                () -> mapper.mapClientRequestToEntity(mockClientRequest));

        // Verify the results
        Assertions.assertEquals("email is marked non-null but is null", ex.getMessage());
    }

    @Test
    void mapClientEntityToClientResponseSuccess() {
        // Execute test
        ClientResponse clientResponse = mapper.mapClientEntityToClientResponse(mockClient);

        // Verify the results
        Assertions.assertEquals(mockClient.getEmail(), clientResponse.getEmail());
        Assertions.assertEquals(mockClient.getId(), clientResponse.getId());
    }

    @Test
    void mapClientEntityToClientResponseEmptyEmotionsSuccess() {
        // Prepare test data
        Emotion mockEmotion = new Emotion();
        mockEmotion.setText("test");
        mockEmotion.setId(mockClient.getId());
        mockEmotion.setType(EmotionType.MIDDLE);

        mockClient.setEmotions(List.of(mockEmotion));

        // Execute test
        ClientResponse clientResponse = mapper.mapClientEntityToClientResponse(mockClient);

        // Verify the results
        Assertions.assertEquals(mockClient.getEmail(), clientResponse.getEmail());
        Assertions.assertEquals(mockClient.getId(), clientResponse.getId());
        assert clientResponse.getEmotions() != null;
        Assertions.assertEquals(mockEmotion.getText(), clientResponse.getEmotions().getFirst().getText());
        Assertions.assertEquals(1, clientResponse.getEmotions().size());
        Assertions.assertEquals(EmotionType.MIDDLE, clientResponse.getEmotions().getFirst().getType());
        Assertions.assertEquals(mockEmotion.getId(), clientResponse.getEmotions().getFirst().getId());
    }

    @Test
    void mapEmotionRequestToEntitySuccess() {

        // Execute test
        Emotion emotion = mapper.mapEmotionRequestToEntity(mockEmotionRequest);

        // Verify the results
        Assertions.assertEquals(mockEmotionRequest.getText(), emotion.getText());
        Assertions.assertEquals(mockEmotionRequest.getType(), emotion.getType());
        Assertions.assertEquals(mockEmotionRequest.getClient().getEmail(), emotion.getClient().getEmail());
    }

    @Test
    void mapEmotionRequestToEntitySuccessButEmotionTypeIsNull() {
        // Prepare test data
        mockEmotionRequest.setType(null);

        // Execute test
        Emotion emotion = mapper.mapEmotionRequestToEntity(mockEmotionRequest);

        // Verify the results
        Assertions.assertEquals(mockEmotionRequest.getText(), emotion.getText());
        Assertions.assertNull(emotion.getType());
        Assertions.assertEquals(mockEmotionRequest.getClient().getEmail(), emotion.getClient().getEmail());
    }

    @Test
    void mapEmotionEntityToResponse() {

        // Prepare test data
        mockEmotion.setId(1L);
        ClientResponse clientResponse = new ClientResponse(1L, "test@local.mail", List.of());

        // Execute test
        EmotionResponse emotionResponse = mapper.mapEmotionEntityToResponse(mockEmotion);

        // Verify the results
        Assertions.assertEquals(mockEmotion.getText(), emotionResponse.getText());
        Assertions.assertEquals(mockEmotion.getType(), emotionResponse.getType());
        Assertions.assertEquals(mockEmotion.getId(), emotionResponse.getId());
        Assertions.assertEquals(mockEmotion.getClient().getEmail(), emotionResponse.getClient().getEmail());
    }
}

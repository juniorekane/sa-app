package com.jekdev.saappapi.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.jekdev.saappapi.base.EmotionType;
import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.dto.EmotionRequest;
import com.jekdev.saappapi.dto.EmotionResponse;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.entities.Emotion;
import com.jekdev.saappapi.errorhandling.ElementNotFoundException;
import com.jekdev.saappapi.errorhandling.PresentElementException;
import com.jekdev.saappapi.mapper.AppMapper;
import com.jekdev.saappapi.repositories.EmotionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmotionServiceTest {

  @Mock private ClientService mockClientService;

  @Mock private AppMapper appMapper;

  @Mock private EmotionRepository mockEmotionRepository;

  @InjectMocks private EmotionService emotionService;

  private EmotionRequest mockEmotionRequest;

  private Emotion mockEmotion;

  private Client mockClient;

  private final Long mockEmotionId = 1L;

  @BeforeEach
  void setUp() {

    ClientRequest mockClientRequest = new ClientRequest();
    mockClientRequest.setEmail("test@local.mail");
    mockEmotionRequest = new EmotionRequest("Test text", EmotionType.BAD, mockClientRequest);

    mockClient = new Client();
    mockClient.setEmail(mockClientRequest.getEmail());
    mockClient.setId(1L);

    mockEmotion = new Emotion();
    mockEmotion.setText(mockEmotionRequest.getText());
    mockEmotion.setType(mockEmotionRequest.getType());
    mockEmotion.setClient(mockClient);
  }

  @Test
  @DisplayName("Should create a new emotion successfully")
  void createEmotionSuccess() {
    // Prepare stubbing for mapper and repository
    when(appMapper.mapEmotionRequestToEntity(mockEmotionRequest)).thenReturn(mockEmotion);
    when(mockClientService.readOrCreateClient(mockClient)).thenReturn(mockClient);
    when(mockEmotionRepository.findByText(mockEmotion.getText())).thenReturn(Optional.empty());

    // Execute test
    emotionService.createEmotion(mockEmotionRequest);

    // Verify the results
    verify(appMapper).mapEmotionRequestToEntity(mockEmotionRequest);
    verify(mockClientService).readOrCreateClient(mockClient);
    verify(mockEmotionRepository).findByText(mockEmotion.getText());
    verify(mockEmotionRepository).save(mockEmotion);
    verifyNoMoreInteractions(mockEmotionRepository);
  }

  @Test
  void createEmotionFailedDueToPresentElementException() {
    // Prepare stubbing for mapper and repository
    when(appMapper.mapEmotionRequestToEntity(mockEmotionRequest)).thenReturn(mockEmotion);
    when(mockClientService.readOrCreateClient(mockClient)).thenReturn(mockClient);
    when(mockEmotionRepository.findByText(mockEmotion.getText())).thenReturn(Optional.of(mockEmotion));

    // Execute test
    PresentElementException ex =
        Assertions.assertThrows(PresentElementException.class, () -> emotionService.createEmotion(mockEmotionRequest));

    // Verify the results
    Assertions.assertEquals("Emotion already exists. Please use a different text.", ex.getMessage());
    verify(appMapper).mapEmotionRequestToEntity(mockEmotionRequest);
    verify(mockClientService).readOrCreateClient(mockClient);
    verify(mockEmotionRepository).findByText(mockEmotion.getText());
    verifyNoMoreInteractions(mockEmotionRepository);
  }

  @Test
  void findAllEmotionSuccess() {

    // Prepare test data
    EmotionResponse mockEmotionResponse =
        new EmotionResponse(
            mockEmotion.getId(),
            mockEmotion.getText(),
            mockEmotion.getType(),
            new ClientResponse(mockEmotion.getClient().getId(), mockEmotion.getClient().getEmail()));

    // Prepare stubbing for repository
    when(mockEmotionRepository.findAll()).thenReturn(List.of(mockEmotion));
    when(appMapper.mapEmotionEntityToResponse(mockEmotion)).thenReturn(mockEmotionResponse);

    // Execute test
    List<EmotionResponse> emotionResponses = emotionService.findAllEmotion();

    // Verify the results
    Assertions.assertEquals(1, emotionResponses.size());
    Assertions.assertEquals(mockEmotion.getId(), emotionResponses.getFirst().getId());
    Assertions.assertEquals(mockEmotion.getText(), emotionResponses.getFirst().getText());
    Assertions.assertEquals(mockEmotion.getType(), emotionResponses.getFirst().getType());
    Assertions.assertEquals(mockEmotion.getClient().getEmail(), emotionResponses.getFirst().getClient().getEmail());
    verify(mockEmotionRepository).findAll();
    verify(appMapper).mapEmotionEntityToResponse(mockEmotion);
    verifyNoMoreInteractions(mockEmotionRepository);
  }

  @Test
  void findAllEmotionFailedDueToEmptyListException() {
    // Prepare stubbing for repository
    when(mockEmotionRepository.findAll()).thenReturn(List.of());

    // Execute test
    ElementNotFoundException ex =
        Assertions.assertThrows(ElementNotFoundException.class, () -> emotionService.findAllEmotion());

    // Verify the results
    Assertions.assertEquals("No emotions found, please create some emotions first.", ex.getMessage());
    verify(mockEmotionRepository).findAll();
    verifyNoMoreInteractions(mockEmotionRepository);
    verifyNoInteractions(appMapper);
  }

  @Test
  void deleteEmotionSuccess() {

    // Prepare stubbing for repository
    when(mockEmotionRepository.findById(mockEmotionId)).thenReturn(Optional.of(mockEmotion));

    // Execute test
    emotionService.deleteEmotion(mockEmotionId);

    // Verify the results
    verify(mockEmotionRepository).findById(mockEmotionId);
    verify(mockEmotionRepository).deleteById(mockEmotionId);
    verifyNoMoreInteractions(mockEmotionRepository);
  }

  @Test
  void deleteEmotionFailedDueToElementNotFoundException() {

    // Prepare stubbing for repository
    when(mockEmotionRepository.findById(mockEmotionId)).thenReturn(Optional.empty());

    // Execute test
    ElementNotFoundException ex =
        Assertions.assertThrows(ElementNotFoundException.class, () -> emotionService.deleteEmotion(mockEmotionId));

    // Verify the results
    Assertions.assertEquals("Emotion with id " + mockEmotionId + " not found.", ex.getMessage());
    verify(mockEmotionRepository).findById(mockEmotionId);
    verifyNoMoreInteractions(mockEmotionRepository);
  }
}

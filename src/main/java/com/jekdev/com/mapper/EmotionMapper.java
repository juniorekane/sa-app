package com.jekdev.com.mapper;

import com.jekdev.com.dto.ClientResponse;
import com.jekdev.com.dto.EmotionRequest;
import com.jekdev.com.dto.EmotionResponse;
import com.jekdev.com.entities.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmotionMapper {

  private final ClientMapper clientMapper;

  public Emotion mapEmotionRequestToEntity(EmotionRequest emotionRequest) {
    Emotion emotion = new Emotion();
    emotion.setText(emotionRequest.getText());
    if (emotionRequest.getType() != null) {
      emotion.setType(emotionRequest.getType());
    }
    emotion.setClient(clientMapper.mapToClientRequestToEntity(emotionRequest.getClient()));
    return emotion;
  }

  public EmotionResponse mapEmotionEntityToResponse(Emotion emotion) {
    ClientResponse clientResponse = clientMapper.mapClientEntityToClientResponse(emotion.getClient());
    return new EmotionResponse(emotion.getId(), emotion.getText(), emotion.getType(), clientResponse);
  }
}

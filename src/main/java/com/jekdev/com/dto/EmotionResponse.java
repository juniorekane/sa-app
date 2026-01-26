package com.jekdev.com.dto;

import com.jekdev.com.base.EmotionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class EmotionResponse {

  private final Long id;

  private final String text;

  private final EmotionType type;

  private final ClientResponse client;
}

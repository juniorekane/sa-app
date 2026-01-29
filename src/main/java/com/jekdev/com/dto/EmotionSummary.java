package com.jekdev.com.dto;

import com.jekdev.com.base.EmotionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EmotionSummary {
  private final Long id;
  private final String text;
  private final EmotionType type;
}

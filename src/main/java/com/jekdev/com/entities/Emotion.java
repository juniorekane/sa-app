package com.jekdev.com.entities;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jekdev.com.base.EmotionType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Emotion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NonNull
  private String text;

  @Column(nullable = false)
  @NonNull
  private EmotionType type;

  @ManyToOne(
      optional = false,
      cascade = {PERSIST, MERGE})
  @JoinColumn(name = "client_id", nullable = false)
  @NonNull
  @JsonBackReference
  private Client client;
}

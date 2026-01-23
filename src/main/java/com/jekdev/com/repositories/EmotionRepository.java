package com.jekdev.com.repositories;

import com.jekdev.com.entities.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}

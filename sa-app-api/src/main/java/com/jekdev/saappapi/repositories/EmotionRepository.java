package com.jekdev.saappapi.repositories;

import com.jekdev.saappapi.entities.Emotion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByText(String text);
}

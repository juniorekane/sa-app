package com.jekdev.saappapi.entities;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an emotion entity in the system.
 * <p>
 * This class is used to capture and persist emotional states associated with a specific client. Each emotion is
 * uniquely identified, categorized by its type, and linked to a client. Fields: - {@code id}: A unique, auto-generated
 * identifier for the emotion. - {@code text}: A description of the emotion, which is mandatory. - {@code type}: The
 * type of the emotion, provided by an external sentiment provider. This field is optional. - {@code client}: The
 * client
 * associated with this emotion. This association is mandatory and uses a many-to-one relationship.
 */
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
    @Nullable
    private String type;

    @Column(nullable = true)
    @Nullable
    private Double score;

    @ManyToOne(optional = false, cascade = { PERSIST, MERGE })
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    @JsonBackReference
    private Client client;
}

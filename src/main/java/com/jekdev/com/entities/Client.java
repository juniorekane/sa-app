package com.jekdev.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a client entity in the system.
 *<p>
 * Each client is identified by a unique ID and has an associated email address.
 * A client optionally has a one-to-one relationship with an emotion entity,
 * representing their emotional state.
 *</p>
 * Fields:<br>
 * - {@code id}: A unique identifier for the client, which is auto-generated.<br>
 * - {@code email}: The email address of the client, which is mandatory.<br>
 * - {@code emotion}: A one-to-one relationship linking this client to an emotion entity.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "client")
    private Emotion emotion;

}

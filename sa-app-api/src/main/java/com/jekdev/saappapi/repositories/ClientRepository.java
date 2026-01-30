package com.jekdev.saappapi.repositories;

import com.jekdev.saappapi.entities.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Client} entities.
 * <p>
 * Provides methods for performing CRUD operations and custom query functionality on the {@link Client} entity. This
 * interface extends {@link JpaRepository}, granting access to a wide range of persistence-related methods.
 * <p>
 * Key Features: - Supports retrieval of a {@link Client} by their email address. - Automatically integrates with Spring
 * Data JPA for query execution and transaction management.
 * <p>
 * Usage: This interface is intended to be used in service classes or other components that require access to client
 * data.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
}

package com.jekdev.com.service;

import com.jekdev.com.entities.Client;
import com.jekdev.com.repositories.ClientRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Client} entities. Provides methods for creating, retrieving, and
 * searching client records in the database.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

  private final ClientRepository clientRepository;

  /**
   * Persists a {@link Client} entity in the database and logs the creation event.
   *
   * @param client the client entity to be saved; must not be null
   */
  public void createClient(Client client) {
    if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
      log.info("Client already exits");
    } else {
      clientRepository.save(client);
      log.info("Created client with id ({})", client.getId());
    }
  }

  /**
   * Retrieves a list of all {@code Client} entities from the database.
   *
   * @return a list of {@code Client} entities representing all clients in the database
   */
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  /**
   * Searches for a {@link Client} entity in the database by its unique identifier.
   *
   * @param id the unique identifier of the {@code Client} to be searched; must not be null
   * @return an {@code Optional} containing the found {@code Client} if it exists, or an empty {@code Optional} if no
   *     client is found
   */
  public Optional<Client> searchClient(Long id) {
    return clientRepository.findById(id);
  }

  /**
   * Retrieves an existing {@link Client} entity from the database based on its email address. If no existing
   * clientToValidate is found, the provided clientToValidate is saved to the database and returned.
   *
   * @param clientToValidate the {@link Client} entity to be retrieved or created; must not be null
   * @return the existing or newly created {@link Client} entity
   */
  public Client readOrCreateClient(Client clientToValidate) {
    Optional<Client> optionalClient = clientRepository.findByEmail(clientToValidate.getEmail());
    if (optionalClient.isPresent()) {
      return optionalClient.get();
    } else {
      clientRepository.save(clientToValidate);
      return clientToValidate;
    }
  }
}

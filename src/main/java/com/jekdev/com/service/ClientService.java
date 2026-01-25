package com.jekdev.com.service;

import com.jekdev.com.dto.ClientRequest;
import com.jekdev.com.dto.ClientResponse;
import com.jekdev.com.entities.Client;
import com.jekdev.com.mapper.ClientMapper;
import com.jekdev.com.repositories.ClientRepository;
import java.util.List;
import java.util.NoSuchElementException;
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

  private final ClientMapper clientMapper;

  private final ClientRepository clientRepository;

  /**
   * Creates a new client in the database based on the provided {@link ClientRequest}. If a client with the same email
   * already exists, the client will not be created, and a log entry will indicate that the client already exists.
   * Otherwise, the client is saved, and a log entry is recorded with the ID of the newly created client.
   *
   * @param clientRequest the request object containing the information of the client to be created; must not be null
   */
  public void createClient(ClientRequest clientRequest) {
    log.info("Creating client with email ({})", clientRequest.getEmail());

    Client client = clientMapper.mapToClientRequestToEntity(clientRequest);

    if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
      log.info("Client already exits");
    } else {
      clientRepository.save(client);
      log.info("Created client with id ({})", client.getId());
    }
  }

  /**
   * Retrieves a list of all clients from the database, maps the client entities to {@link ClientResponse} objects, and
   * returns the resulting list. Each {@link ClientResponse} contains the relevant details of a client, such as the
   * unique identifier and email address.
   *
   * @return a {@link List} of {@link ClientResponse} objects representing all clients stored in the database
   */
  public List<ClientResponse> getAllClients() {
    return clientRepository.findAll().stream().map(clientMapper::mapClientEntityToClientResponse).toList();
  }

  /**
   * Searches for a {@link Client} entity in the database based on the provided unique identifier. If the client is
   * found, it is mapped to a {@link ClientResponse} object.
   *
   * @param id the unique identifier of the client to search for; must not be null
   * @return a {@link ClientResponse} containing the client's details if the client is found
   * @throws NoSuchElementException if no client exists with the specified ID
   */
  public ClientResponse searchClient(Long id) {

    log.info("Searching for client with id ({})", id);

    Client client = clientRepository.findById(id).orElseThrow();

    return clientMapper.mapClientEntityToClientResponse(client);
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

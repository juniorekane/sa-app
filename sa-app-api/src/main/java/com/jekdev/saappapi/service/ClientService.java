package com.jekdev.saappapi.service;

import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.errorhandling.ElementNotFoundException;
import com.jekdev.saappapi.errorhandling.PresentElementException;
import com.jekdev.saappapi.mapper.AppMapper;
import com.jekdev.saappapi.repositories.ClientRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClientService is a service layer component responsible for handling business logic and operations related to client
 * management. This class interacts with the underlying repository layer to perform CRUD operations on client data and
 * provides functionality to map entities and responses using {@link AppMapper}.
 * <p>
 * Key functionalities include: - Creating a client while ensuring no duplicates exist based on email. - Retrieving all
 * clients and mapping entities to response objects. - Searching for clients using a unique identifier. - Reading or
 * creating a client based on its email address.
 * <p>
 * This class makes use of logging to track operations and exceptions, ensuring transparency and easier debugging during
 * runtime.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final AppMapper appMapper;

    private final ClientRepository clientRepository;

    /**
     * Creates a new client in the database based on the provided {@link ClientRequest}. If a client with the same email
     * already exists, the client will not be created, and a log entry will indicate that the client already exists.
     * Otherwise, the client is saved, and a log entry is recorded with the ID of the newly created client.
     *
     * @param clientRequest
     *            the request object containing the information of the client to be created; must not be null
     */
    public void createClient(ClientRequest clientRequest) {
        log.info("Creating client with email ({})", clientRequest.getEmail());

        Client client = appMapper.mapClientRequestToEntity(clientRequest);

        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            log.info("Client already exits");
            throw new PresentElementException("Client already exists. Please use a different email address.");
        } else {
            clientRepository.save(client);
            log.info("Created client with id ({})", client.getId());
        }
    }

    /**
     * Retrieves a list of all clients from the database, maps the client entities to {@link ClientResponse} objects,
     * and returns the resulting list. Each {@link ClientResponse} contains the relevant details of a client, such as
     * the unique identifier and email address.
     *
     * @return a {@link List} of {@link ClientResponse} objects representing all clients stored in the database
     */
    public List<ClientResponse> getAllClients() {

        log.info("Fetching all clients");
        List<Client> clientList = clientRepository.findAll();

        if (clientList.isEmpty()) {
            log.info("No clients found");
            throw new ElementNotFoundException("No clients found, please create some clients first.");
        }
        log.info("Fetched all clients");
        return clientList.stream().map(appMapper::mapClientEntityToClientResponse).toList();
    }

    /**
     * Searches for a {@link Client} entity in the database based on the provided unique identifier. If the client is
     * found, it is mapped to a {@link ClientResponse} object.
     *
     * @param id
     *            the unique identifier of the client to search for; must not be null
     *
     * @return a {@link ClientResponse} containing the client's details if the client is found
     *
     * @throws ElementNotFoundException
     *             if no client exists with the specified ID
     */
    public ClientResponse searchClient(Long id) {

        log.info("Searching for client with id ({})", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Client with id " + id + " not found."));

        return appMapper.mapClientEntityToClientResponse(client);
    }

    /**
     * Retrieves an existing {@link Client} entity from the database based on its email address. If no existing
     * clientToValidate is found, the provided clientToValidate is saved to the database and returned.
     *
     * @param clientToValidate
     *            the {@link Client} entity to be retrieved or created; must not be null
     *
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

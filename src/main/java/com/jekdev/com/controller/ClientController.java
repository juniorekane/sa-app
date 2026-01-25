package com.jekdev.com.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.com.entities.Client;
import com.jekdev.com.service.ClientService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ClientController.BASE_PATH)
@RequiredArgsConstructor
public class ClientController {
  /**
   * The base path used for mapping HTTP requests to the endpoints of the {@code ClientController}. This constant is
   * used in the {@code @RequestMapping} annotation to define the root URL for all endpoints in the {@code
   * ClientController}.
   */
  public static final String BASE_PATH = "client";

  public static final String CLIENT_LIST_PATH = "find_all";

  public static final String SINGLE_ID_CLIENT_PATH = "search/{id}";

  private final ClientService clientService;

  /**
   * Creates a new client entity in the system. This method processes a POST request with a JSON body containing client
   * details and persists the client information in the database.
   *
   * @param client the client entity to be created; must not be null
   */
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public void create(@RequestBody Client client) {
    clientService.createClient(client);
  }

  /**
   * Handles the HTTP GET request for retrieving a list of all clients.
   *
   * @return a list of {@code Client} entities, each representing a client in the system
   */
  @GetMapping(value = CLIENT_LIST_PATH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public List<Client> getClients() {
    return clientService.getAllClients();
  }

  /**
   * Searches for a {@link Client} entity based on its unique identifier. Handles an HTTP GET request and returns the
   * client if found.
   *
   * @param id the unique identifier of the client; must not be null
   * @return an {@code Optional} containing the {@code Client} entity if found, or an empty {@code Optional} if no
   *     client exists with the specified ID
   */
  @GetMapping(value = SINGLE_ID_CLIENT_PATH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public Optional<Client> searchClientWithID(@PathVariable Long id) {
    return clientService.searchClient(id);
  }
}

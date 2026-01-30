package com.jekdev.saappapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.service.ClientService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for managing client-related endpoints. This controller provides several HTTP-based APIs
 * for performing operations such as creating new clients, retrieving clients by their unique identifier, and managing
 * client data.
 * <p>
 * The controller uses the {@link ClientService} to delegate the actual processing logic and interaction with the data
 * layer. Endpoints are exposed under the base path specified by {@link ClientController#BASE_PATH}. All API operations
 * in this controller adhere to RESTful principles, returning JSON payloads for data representation and appropriate HTTP
 * status codes for response statuses.
 * <p>
 * Annotations: - {@code @RestController}: Indicates that this is a REST controller where methods return domain objects
 * as JSON instead of rendering views. - {@code @RequestMapping(value = ClientController.BASE_PATH)}: Specifies the base
 * route for all endpoints defined in this controller. - {@code @RequiredArgsConstructor}: Automatically generates a
 * constructor for required fields.
 */
@RestController
@RequestMapping(value = ClientController.BASE_PATH)
@RequiredArgsConstructor
public class ClientController {
    /**
     * The base path used for mapping HTTP requests to the endpoints of the {@code ClientController}. This constant is
     * used in the {@code @RequestMapping} annotation to define the root URL for all endpoints in the {@code
     * ClientController}.
     */
    public static final String BASE_PATH = "/client";

    /**
     * A string constant representing the path segment for retrieving a list of all clients. This value is used in
     * constructing the endpoint URL for handling HTTP GET requests that fetch all client records.
     * <p>
     * In the context of the {@code ClientController}, this constant specifies the relative path for the "find all
     * clients" operation. The full endpoint URL is constructed by combining the {@code BASE_PATH} of the controller and
     * this constant.
     */
    public static final String CLIENT_LIST_PATH = "/find_all";

    /**
     * A string constant representing the path segment for searching a single client by their unique identifier. This
     * value defines a dynamic URL segment in the endpoint mapping for the {@code ClientController} and is used to
     * handle HTTP GET requests where an individual client's details are retrieved based on their ID.
     * <p>
     * The path includes a placeholder `{id}` that must be replaced with the actual client ID during the request. The
     * final URL path for this endpoint is constructed by combining the {@code BASE_PATH} of the controller with this
     * constant.
     */
    public static final String SINGLE_ID_CLIENT_PATH = "/search/{id}";

    public static final String CREATE_PATH = "/create";

    private final ClientService clientService;

    /**
     * Creates a new client based on the provided request data. This method accepts a JSON payload representing the
     * client's information, validates it, and attempts to create the client in the database. If the creation is
     * successful, the method responds with an HTTP status of 201 (Created).
     *
     * @param clientRequest
     *            the request object containing the information for the client to be created; must be a valid JSON
     *            payload and conform to the {@code ClientRequest} structure
     *
     * @return a {@link ResponseEntity} with an HTTP status of 201 (Created) if the client is successfully created
     */
    @PostMapping(value = CREATE_PATH, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody ClientRequest clientRequest) {
        clientService.createClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Handles HTTP GET requests to search for a client by their unique identifier. This method retrieves the client's
     * details using the provided ID and returns a JSON representation of the {@code ClientResponse}.
     *
     * @param id
     *            the unique identifier of the client to be searched; must not be null
     *
     * @return a {@link ResponseEntity} containing the client's information in JSON format, with an HTTP status of 200
     *         (OK) if the client is found
     */
    @GetMapping(value = SINGLE_ID_CLIENT_PATH, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Map<String, String>> searchClientWithID(@Valid @PathVariable Long id) {
        ClientResponse clientResponse = clientService.searchClient(id);
        Map<String, String> body = Map.of("id", clientResponse.getId().toString(), "email", clientResponse.getEmail());
        return ResponseEntity.ok().body(body);
    }
}

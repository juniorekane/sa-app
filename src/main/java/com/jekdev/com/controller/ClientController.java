package com.jekdev.com.controller;

import com.jekdev.com.entities.Client;
import com.jekdev.com.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = ClientController.BASE_PATH)
@RequiredArgsConstructor
public class ClientController {
    /**
     * The base path used for mapping HTTP requests to the endpoints of the {@code ClientController}.
     * This constant is used in the {@code @RequestMapping} annotation to define the root URL for all
     * endpoints in the {@code ClientController}.
     */
    public static final String BASE_PATH = "client";

    public static final String CLIENT_LIST_PATH = "find_all";

    public static final String SINGLE_ID_CLIENT_PATH = "search/{id}";

    private final ClientService clientService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void create(@RequestBody Client client){
        clientService.createClient(client);
    }

    @GetMapping(value = CLIENT_LIST_PATH, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Client> getClients(){
        return clientService.getAllClients();
    }

    @GetMapping(value = SINGLE_ID_CLIENT_PATH, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Optional<Client> searchClientWithID(@PathVariable Long id){
        return clientService.searchClient(id);
    }

}

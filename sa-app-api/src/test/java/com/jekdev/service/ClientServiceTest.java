package com.jekdev.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.entities.Client;
import com.jekdev.saappapi.errorhandling.ElementNotFoundException;
import com.jekdev.saappapi.errorhandling.PresentElementException;
import com.jekdev.saappapi.mapper.AppMapper;
import com.jekdev.saappapi.repositories.ClientRepository;
import com.jekdev.saappapi.service.ClientService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private AppMapper mockClientMapper;

    @Mock
    private ClientRepository mockClientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client mockClient;

    private ClientRequest mockClientRequest;

    @BeforeEach
    void setUp() {
        // Prepare the test data
        mockClientRequest = new ClientRequest();
        mockClientRequest.setEmail("test@local.mail");

        mockClient = new Client();
        mockClient.setEmail(mockClientRequest.getEmail());
        mockClient.setId(1L);
        mockClient.setEmotions(null);
    }

    @Test
    @DisplayName("Should create a new client successfully")
    void createClientSuccess() {
        // Prepare stubbing for mapper and repository
        when(mockClientMapper.mapClientRequestToEntity(mockClientRequest)).thenReturn(mockClient);
        when(mockClientRepository.findByEmail(mockClientRequest.getEmail())).thenReturn(Optional.empty());

        // Execute the test
        clientService.createClient(mockClientRequest);

        // Verify the results
        verify(mockClientMapper).mapClientRequestToEntity(mockClientRequest);
        verify(mockClientRepository).findByEmail(mockClientRequest.getEmail());
        verify(mockClientRepository).save(mockClient);
    }

    @Test
    @DisplayName("Should show PresentElementException when client email already exists")
    void createClientFailure() {
        // Prepare stubbing for mapper and repository
        when(mockClientMapper.mapClientRequestToEntity(mockClientRequest)).thenReturn(mockClient);
        when(mockClientRepository.findByEmail(mockClientRequest.getEmail())).thenReturn(Optional.of(mockClient));

        // Execute test
        PresentElementException ex = Assertions.assertThrows(PresentElementException.class,
                () -> clientService.createClient(mockClientRequest));

        // Verify the results
        Assertions.assertEquals("Client already exists. Please use a different email address.", ex.getMessage());
        verify(mockClientMapper).mapClientRequestToEntity(mockClientRequest);
        verify(mockClientRepository).findByEmail(mockClientRequest.getEmail());
        verifyNoMoreInteractions(mockClientRepository);
    }

    @Test
    @DisplayName("Should return all clients successfully")
    void getAllClientsSuccess() {
        ClientResponse expectedClientResponse = new ClientResponse(mockClient.getId(), mockClient.getEmail());

        // Prepare stubbing for repository
        when(mockClientRepository.findAll()).thenReturn(List.of(mockClient));
        when(mockClientMapper.mapClientEntityToClientResponse(mockClient)).thenReturn(expectedClientResponse);

        // Execute test
        List<ClientResponse> clientResponses = clientService.getAllClients();

        // Verify the results
        Assertions.assertEquals(1, clientResponses.size());
        Assertions.assertEquals(expectedClientResponse.getEmail(), clientResponses.getFirst().getEmail());
        Assertions.assertEquals(expectedClientResponse.getId(), clientResponses.getFirst().getId());
        verify(mockClientRepository).findAll();
        verify(mockClientMapper).mapClientEntityToClientResponse(mockClient);
    }

    @Test
    @DisplayName("Should return empty list when no clients exist")
    void getAllClientsFailureWithNoClients() {

        // Prepare stubbing for repository
        when(mockClientRepository.findAll()).thenReturn(List.of());

        // Execute test
        ElementNotFoundException exception = Assertions.assertThrows(ElementNotFoundException.class,
                () -> clientService.getAllClients());

        // Verify the results
        Assertions.assertEquals("No clients found, please create some clients first.", exception.getMessage());
        verify(mockClientRepository).findAll();
    }

    @Test
    @DisplayName("Should return client details successfully")
    void searchClientSuccess() {

        // Prepare stubbing for repository
        when(mockClientRepository.findById(mockClient.getId())).thenReturn(Optional.of(mockClient));
        when(mockClientMapper.mapClientEntityToClientResponse(mockClient))
                .thenReturn(new ClientResponse(mockClient.getId(), mockClient.getEmail()));

        // Execute test
        ClientResponse clientResponse = clientService.searchClient(mockClient.getId());

        // Verify the results
        Assertions.assertEquals(mockClient.getEmail(), clientResponse.getEmail());
        Assertions.assertEquals(mockClient.getId(), clientResponse.getId());
        verify(mockClientRepository).findById(mockClient.getId());
        verify(mockClientMapper).mapClientEntityToClientResponse(mockClient);
    }

    @Test
    @DisplayName("Should throw ElementNotFoundException when client not found")
    void searchClientFailureNoClientFound() {

        // Prepare stubbing for repository
        when(mockClientRepository.findById(mockClient.getId())).thenReturn(Optional.empty());

        // Execute test
        ElementNotFoundException exception = Assertions.assertThrows(ElementNotFoundException.class,
                () -> clientService.searchClient(mockClient.getId()));

        // Verify the results
        Assertions.assertEquals("Client with id " + mockClient.getId() + " not found.", exception.getMessage());
        verify(mockClientRepository).findById(mockClient.getId());
    }

    @Test
    @DisplayName("Should return existing client")
    void readOrCreateClientFound() {
        // Prepare stubbing for repository
        when(mockClientRepository.findByEmail(mockClient.getEmail())).thenReturn(Optional.of(mockClient));

        // Execute test
        Client client = clientService.readOrCreateClient(mockClient);

        // Verify the results
        Assertions.assertEquals(mockClient.getEmail(), client.getEmail());
        Assertions.assertEquals(mockClient.getId(), client.getId());
        verify(mockClientRepository).findByEmail(mockClient.getEmail());
        verifyNoMoreInteractions(mockClientRepository);
    }

    @Test
    @DisplayName("Should create new client when not found")
    void readOrCreateClientNotFound() {

        // Prepare stubbing for repository
        when(mockClientRepository.findByEmail(mockClient.getEmail())).thenReturn(Optional.empty());

        // Execute test
        Client client = clientService.readOrCreateClient(mockClient);

        // Verify the results
        Assertions.assertEquals(mockClient.getEmail(), client.getEmail());
        Assertions.assertEquals(mockClient.getId(), client.getId());
        verify(mockClientRepository).findByEmail(mockClient.getEmail());
        verify(mockClientRepository).save(mockClient);
    }
}

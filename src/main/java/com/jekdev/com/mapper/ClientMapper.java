package com.jekdev.com.mapper;

import com.jekdev.com.dto.ClientRequest;
import com.jekdev.com.dto.ClientResponse;
import com.jekdev.com.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

  public Client mapToClientRequestToEntity(ClientRequest clientRequest) {
    Client client = new Client();
    client.setEmail(clientRequest.getEmail());
    return client;
  }

  public ClientResponse mapClientEntityToClientResponse(Client client) {
    if (client.getEmotions().isEmpty()) {
      return new ClientResponse(client.getId(), client.getEmail());
    }
    return new ClientResponse(client.getId(), client.getEmail(), client.getEmotions());
  }
}

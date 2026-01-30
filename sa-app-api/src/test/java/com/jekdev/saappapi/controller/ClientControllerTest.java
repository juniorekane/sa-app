package com.jekdev.saappapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

  @MockitoBean private ClientService clientService;

  @Autowired private MockMvc mockMvc;

  private static final String EMAIL = "test@mail.local";

  @Test
  void createClientWithCorrectEmail() throws Exception {
    // Configure the client Request
    String clientRequest =
        """
        {
        "email": "%s"
        }
        """
            .formatted(EMAIL);

    String CREATE_PATH = ClientController.BASE_PATH + ClientController.CREATE_PATH;
    mockMvc
        .perform(MockMvcRequestBuilders.post(CREATE_PATH).content(clientRequest).contentType(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void searchClientWithIDSuccess() throws Exception {

    Long CLIENT_ID = 1L;
    ClientResponse clientResponse = new ClientResponse(CLIENT_ID, EMAIL);

    when(clientService.searchClient(Mockito.anyLong())).thenReturn(clientResponse);
    String SEARCH_PATH = ClientController.BASE_PATH + ClientController.SINGLE_ID_CLIENT_PATH;
    mockMvc
        .perform(MockMvcRequestBuilders.get(SEARCH_PATH, CLIENT_ID).contentType(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(CLIENT_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL));
  }
}

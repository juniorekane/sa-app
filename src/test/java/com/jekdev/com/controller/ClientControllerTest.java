package com.jekdev.com.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.jekdev.com.dto.ClientResponse;
import com.jekdev.com.service.ClientService;
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

  private final String CREATE_PATH = ClientController.BASE_PATH + ClientController.CREATE_PATH;

  private final String CLIENT_ID = "1";

  private final String SEARCH_PATH = ClientController.BASE_PATH + "/search/" + CLIENT_ID;

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

    mockMvc
        .perform(MockMvcRequestBuilders.post(CREATE_PATH).content(clientRequest).contentType(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void searchClientWithIDSuccess() throws Exception {

    ClientResponse clientResponse = new ClientResponse(1L, EMAIL);

    when(clientService.searchClient(Mockito.anyLong())).thenReturn(clientResponse);
    mockMvc
        .perform(MockMvcRequestBuilders.get(SEARCH_PATH).contentType(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(CLIENT_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL));
  }
}

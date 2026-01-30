package com.jekdev.errorhandling;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.saappapi.controller.ClientController;
import com.jekdev.saappapi.dto.ClientRequest;
import com.jekdev.saappapi.errorhandling.ElementNotFoundException;
import com.jekdev.saappapi.errorhandling.PresentElementException;
import com.jekdev.saappapi.service.ClientService;
import com.jekdev.saappapi.service.EmotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private EmotionService emotionService;

    private final Long CLIENT_ID = 999L;

    private final String SEARCH_PATH = ClientController.BASE_PATH + ClientController.SINGLE_ID_CLIENT_PATH;

    private final String CREATE_PATH = ClientController.BASE_PATH + ClientController.CREATE_PATH;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test exception handling for ElementNotFoundException")
    void handleElementNotFoundException() throws Exception {

        // Prepare stubbing for client service
        when(clientService.searchClient(CLIENT_ID))
                .thenThrow(new ElementNotFoundException("Client with " + CLIENT_ID + " not found."));

        // Execute test
        mockMvc.perform(MockMvcRequestBuilders.get(SEARCH_PATH, CLIENT_ID))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Client with " + CLIENT_ID + " not found."));
    }

    @Test
    @DisplayName("Test exception handling for PresentElementException")
    void handlePresentElementException() throws Exception {
        // Prepare test data
        String testEmail = "test@local.mail";
        String clientRequest = """
                {
                  "email": "%s"
                  }
                """.formatted(testEmail);

        // Stubbing for client service
        doThrow(new PresentElementException("Client already exists. Please use a different email address."))
                .when(clientService).createClient(any(ClientRequest.class));

        // Execute test
        mockMvc.perform(
                MockMvcRequestBuilders.post(CREATE_PATH).content(clientRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error")
                        .value("Client already exists. Please use a different email address."))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Test exception handling for common validation errors")
    void handleCommonValidationErrors() throws Exception {

        // Prepare test data
        String testEmail = "local.mail";
        String clientRequest = """
                {
                  "email": "%s"
                  }
                """.formatted(testEmail);

        // Execute test
        mockMvc.perform(
                MockMvcRequestBuilders.post(CREATE_PATH).content(clientRequest).contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("MethodArgumentNotValidException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("email: Invalid email address format"));
    }
}

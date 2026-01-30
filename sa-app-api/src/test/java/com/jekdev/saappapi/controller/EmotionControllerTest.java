package com.jekdev.saappapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jekdev.saappapi.base.EmotionType;
import com.jekdev.saappapi.dto.ClientResponse;
import com.jekdev.saappapi.dto.EmotionResponse;
import com.jekdev.saappapi.service.EmotionService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(EmotionController.class)
class EmotionControllerTest {

    @MockitoBean
    private EmotionService emotionService;

    @Autowired
    private MockMvc mockMvc;

    private final Long EMOTION_ID = 1L;

    private final String TEXT = "test";
    private final String TYPE = "HAPPY";
    private final String EMAIL = "test@local.mail";
    private final String emotionRequest = """
            {
                "text": "%s",
                "type": "%s",
                "client": {
                    "email": "%s"
                }
            }\
            """.formatted(TEXT, TYPE, EMAIL);

    @Test
    void createEmotion() throws Exception {

        String CREATE_PATH = EmotionController.BASE_PATH + EmotionController.CREATE_PATH;
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_PATH).content(emotionRequest).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getAllEmotions() throws Exception {
        ClientResponse clientResponse = new ClientResponse(EMOTION_ID, EMAIL);
        EmotionResponse emotionResponse = new EmotionResponse(EMOTION_ID, TEXT, EmotionType.HAPPY, clientResponse);
        when(emotionService.findAllEmotion()).thenReturn(List.of(emotionResponse));

        String ALL_EMOTION_PATH = EmotionController.BASE_PATH + EmotionController.ALL_EMOTION_PATH;
        mockMvc.perform(MockMvcRequestBuilders.get(ALL_EMOTION_PATH).contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value(TEXT))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(EMOTION_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value(EmotionType.HAPPY.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client.id").value(clientResponse.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteEmotion() throws Exception {

        String DELETE_PATH = EmotionController.BASE_PATH + EmotionController.DELETE_PATH;
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, EMOTION_ID).contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

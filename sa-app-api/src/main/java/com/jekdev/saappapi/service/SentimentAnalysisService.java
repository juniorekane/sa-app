package com.jekdev.saappapi.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.jekdev.saappapi.errorhandling.SentimentProviderException;
import com.jekdev.saappapi.utils.SentimentRequest;
import com.jekdev.saappapi.utils.SentimentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class SentimentAnalysisService {

    private final @Qualifier("sentimentRestClient") RestClient sentimentRestClient;
    private final ObjectMapper objectMapper;

    @Value("${sentiment.api.token:}")
    private String apiToken;

    @Value("${sentiment.api.model-path}")
    private String modelPath;

    public SentimentResult analyze(String text) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new SentimentProviderException(
                    "Sentiment API token is missing. Configure 'sentiment.api.token' or environment variable SENTIMENT_API_TOKEN.");
        }

        try {
            String responseBody = sentimentRestClient.post()
                    .uri(modelPath)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new SentimentRequest(text))
                    .retrieve()
                    .body(String.class);

            JsonNode response = objectMapper.readTree(responseBody);
            return parseBestPrediction(response);
        } catch (JacksonException exception) {
            throw new SentimentProviderException("Failed to parse sentiment provider response: " + exception.getMessage(),
                    exception);
        } catch (RestClientException exception) {
            throw new SentimentProviderException("Sentiment provider request failed: " + exception.getMessage(),
                    exception);
        }
    }

    private SentimentResult parseBestPrediction(JsonNode body) {
        if (body == null || body.isNull()) {
            throw new SentimentProviderException("Sentiment provider returned an empty response.");
        }

        if (body.isObject() && body.has("error")) {
            throw new SentimentProviderException("Sentiment provider error: " + body.get("error").asText());
        }

        if (!body.isArray() || body.isEmpty()) {
            throw new SentimentProviderException("Sentiment provider response format is not supported.");
        }

        JsonNode predictions = body.get(0).isArray() ? body.get(0) : body;

        if (!predictions.isArray() || predictions.isEmpty()) {
            throw new SentimentProviderException("Sentiment provider returned no predictions.");
        }

        SentimentResult best = null;

        for (JsonNode prediction : predictions) {
            if (!prediction.isObject()) {
                continue;
            }

            String label = prediction.path("label").asText(null);
            double score = prediction.path("score").asDouble(Double.NaN);

            if (label == null || Double.isNaN(score)) {
                continue;
            }

            if (best == null || score > best.score()) {
                best = new SentimentResult(label, score);
            }
        }

        if (best == null) {
            throw new SentimentProviderException("Sentiment provider returned no usable prediction values.");
        }

        return best;
    }
}

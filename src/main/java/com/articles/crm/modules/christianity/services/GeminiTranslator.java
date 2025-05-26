package com.articles.crm.modules.christianity.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GeminiTranslator {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=%s";

    private final AtomicInteger requestCount = new AtomicInteger(0);
    private volatile LocalDate lastResetDate = LocalDate.now();

    private static final int MAX_REQUESTS_PER_DAY = 200;

    private synchronized void resetCounterIfNeeded() {
        LocalDate today = LocalDate.now();
        if (!today.equals(lastResetDate)) {
            requestCount.set(0);
            lastResetDate = today;
        }
    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public JsonNode translateJson(String json, String originalLanguage, String targetLanguage) {
        resetCounterIfNeeded();

        if (requestCount.incrementAndGet() > MAX_REQUESTS_PER_DAY) {
            throw new RuntimeException("Daily request limit exceeded (max " + MAX_REQUESTS_PER_DAY + ")");
        }

        try {
            String prompt = String.format("""
                Translate all the visible text in the following Editor.js JSON from %s to %s.
                Keep the JSON structure exactly the same. Only translate values of fields like "text", "caption", and other user-facing content.
                I will use your answer from server, so make it JSON only.

                ❗ Respond with the full translated JSON only.
                ❗ Do not wrap the output in markdown (no ```json).
                ❗ Do not add any comments, explanations, or formatting — return plain raw JSON only.

                JSON:
                %s
                """, originalLanguage, targetLanguage, json);

            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[]{
                            Map.of("parts", new Object[]{
                                    Map.of("text", prompt)
                            })
                    },
                    "generationConfig", Map.of(
                            "temperature", 0.2,
                            "topK", 1,
                            "topP", 1.0,
                            "maxOutputTokens", 4096,
                            "responseMimeType", "application/json"
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String fullUrl = String.format(GEMINI_URL, apiKey);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<JsonNode> response = restTemplate.exchange(fullUrl, HttpMethod.POST, entity, JsonNode.class);
            JsonNode candidates = response.getBody().path("candidates");
            JsonNode textNode = candidates.path(0).path("content").path("parts").path(0).path("text");

            if (textNode == null || textNode.isMissingNode() || textNode.asText().isBlank()) {
                throw new RuntimeException("Missing or empty translation response from Gemini");
            }

            String translatedText = textNode.asText();
            return objectMapper.readTree(translatedText);

        } catch (Exception e) {
            throw new RuntimeException("Translation failed: " + e.getMessage(), e);
        }
    }
}

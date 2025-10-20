package com.articles.crm.modules.ai.services;

import com.articles.crm.modules.ai.dtos.ChatRequest;
import com.articles.crm.modules.ai.dtos.ChatResponse;
import com.articles.crm.modules.ai.dtos.ReflectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiTextService {
    private static final Logger log = LoggerFactory.getLogger(OpenAiTextService.class);

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.text.default-model:gpt-4.1-mini}")
    private String defaultModel;

    @Value("${openai.text.max-tokens:2000}")
    private Integer defaultMaxTokens;

    @Value("${openai.text.temperature:0.6}")
    private Double defaultTemperature;

    /**
     * Calls OpenAI and returns a parsed ReflectionResult object.
     * Uses JSON mode to force valid JSON output and includes fallback repair.
     */
    public ReflectionResult chatReflectionJson(String systemPrompt, String userPrompt) {
        ChatRequest req = ChatRequest.builder()
                .model(defaultModel)
                .temperature(defaultTemperature)
                .max_tokens(defaultMaxTokens)
                .response_format(new ChatRequest.ResponseFormat("json_object")) // JSON mode
                .messages(List.of(
                        new ChatRequest.Message("system", systemPrompt.trim()),
                        new ChatRequest.Message("user", userPrompt.trim())
                ))
                .build();

        ChatResponse resp = openAiWebClient.post()
                .uri("/v1/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchangeToMono(clientResponse -> {
                    var status = clientResponse.statusCode();
                    if (status.is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ChatResponse.class);
                    }
                    return clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .flatMap(body -> {
                                log.error("OpenAI chat error {}: {}", status.value(), body);
                                return Mono.error(new RuntimeException("OpenAI chat API error: " + status.value() + " - " + body));
                            });
                })
                .timeout(Duration.ofSeconds(60))
                .block();

        if (resp == null || resp.getChoices() == null || resp.getChoices().isEmpty()) {
            throw new RuntimeException("OpenAI returned empty chat response");
        }

        String content = resp.getChoices().get(0).getMessage().getContent();
        if (content == null) {
            throw new RuntimeException("OpenAI returned null content");
        }

        content = content.trim();
        log.debug("Raw AI JSON output (first 400 chars): {}",
                content.substring(0, Math.min(content.length(), 400)));

        try {
            return objectMapper.readValue(content, ReflectionResult.class);
        } catch (Exception parseEx) {
            log.warn("Initial JSON parse failed: {}", parseEx.getMessage());

            // --- Minimal repair fallback for truncated JSON ---
            int lastBrace = content.lastIndexOf('}');
            if (lastBrace > 0) {
                String repaired = content.substring(0, lastBrace + 1);
                try {
                    ReflectionResult fixed = objectMapper.readValue(repaired, ReflectionResult.class);
                    log.info("Recovered from malformed JSON by trimming at {}", lastBrace);
                    return fixed;
                } catch (Exception ignored) {
                    log.error("Repair attempt failed on truncated JSON: {}", ignored.getMessage());
                }
            }

            // --- Last resort: log and throw clean error ---
            log.error("Failed to parse JSON content from model:\n{}", content, parseEx);
            throw new RuntimeException("Failed to parse JSON from model: " + parseEx.getMessage());
        }
    }

    /** Convenience method: returns only the HTML content. */
    public String chatHtml(String systemPrompt, String userPrompt) {
        ReflectionResult rr = chatReflectionJson(systemPrompt, userPrompt);
        return rr.getContentHtml();
    }
}

package com.articles.crm.modules.ai.services;

import com.articles.crm.modules.ai.dtos.OpenAiImageRequest;
import com.articles.crm.modules.ai.dtos.OpenAiImageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiImageService {
    private static final Logger log = LoggerFactory.getLogger(OpenAiImageService.class);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final WebClient openAiWebClient;

    public List<byte[]> generateBytes(OpenAiImageRequest req) {
        if (req == null) throw new IllegalArgumentException("Request cannot be null");
        if (req.getModel() == null || req.getModel().isBlank()) throw new IllegalArgumentException("model is required");
        if (req.getPrompt() == null || req.getPrompt().isBlank()) throw new IllegalArgumentException("prompt is required");

        OpenAiImageResponse resp = openAiWebClient.post()
                .uri("/v1/images/generations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchangeToMono(clientResponse -> {
                    var status = clientResponse.statusCode();
                    if (status.is2xxSuccessful()) {
                        return clientResponse.bodyToMono(OpenAiImageResponse.class);
                    }
                    return clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .flatMap(body -> {
                                String msg = "OpenAI images error " + status.value() + ": " + body;
                                log.error(msg);
                                return Mono.error(new OpenAiApiException(status, body)); // no retries
                            });
                })
                .timeout(Duration.ofSeconds(30))
                .block();

        if (resp == null || resp.getData() == null || resp.getData().isEmpty()) {
            throw new OpenAiApiException(org.springframework.http.HttpStatusCode.valueOf(502),
                    "Empty image data in response");
        }

        try {
            return resp.getData().stream()
                    .map(d -> {
                        if (d.getB64_json() == null || d.getB64_json().isBlank()) {
                            throw new OpenAiApiException(org.springframework.http.HttpStatusCode.valueOf(502),
                                    "Missing b64_json in image data");
                        }
                        return Base64.getDecoder().decode(d.getB64_json());
                    })
                    .toList();
        } catch (IllegalArgumentException badBase64) {
            throw new OpenAiApiException(org.springframework.http.HttpStatusCode.valueOf(502),
                    "Invalid base64 in image data: " + badBase64.getMessage());
        }
    }


    // ---- Exceptions ----

    /** Non-retryable OpenAI error (4xx other than 429). */
    public static class OpenAiApiException extends RuntimeException {
        private final HttpStatusCode status;
        public OpenAiApiException(HttpStatusCode status, String body) {
            super("OpenAI API error " + status.value() + ": " + body);
            this.status = status;
        }
        public HttpStatusCode getStatus() { return status; }
    }
}

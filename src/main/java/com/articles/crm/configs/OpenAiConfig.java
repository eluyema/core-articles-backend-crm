package com.articles.crm.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class OpenAiConfig {
    @Value("${openai.base-url}") private String baseUrl;
    @Value("${openai.api-key}")  private String apiKey;
    @Value("${openai.http.connect-timeout-ms:5000}") private int connectTimeoutMs;
    @Value("${openai.http.read-timeout-ms:120000}")   private int readTimeoutMs;
    @Value("${openai.http.max-in-memory-size-mb:20}") private int maxInMemoryMb;

    @Bean
    public WebClient openAiWebClient() {
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofMillis(readTimeoutMs))
                .doOnConnected(c -> c.addHandlerLast(new ReadTimeoutHandler(readTimeoutMs, TimeUnit.MILLISECONDS)));
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxInMemoryMb * 1024 * 1024))
                        .build())
                .clientConnector(new ReactorClientHttpConnector(http))
                .build();
    }
}
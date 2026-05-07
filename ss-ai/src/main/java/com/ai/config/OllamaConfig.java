package com.ai.config;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class OllamaConfig {

    @Value("${spring.ai.ollama.base-url:http://127.0.0.1:11434}")
    private String baseUrl;

    @Bean
    public RestClient.Builder ollamaRestClientBuilder() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) Duration.ofSeconds(30).toMillis());
        requestFactory.setReadTimeout((int) Duration.ofMinutes(5).toMillis());
        return RestClient.builder().requestFactory(requestFactory);
    }

    @Bean
    public OllamaApi ollamaApi(RestClient.Builder restClientBuilder) {
        return OllamaApi.builder()
                .baseUrl(baseUrl)
                .restClientBuilder(restClientBuilder.build().mutate())
                .build();
    }

    @Bean
    public OllamaChatModel ollamaChatModel(OllamaApi ollamaApi) {
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(org.springframework.ai.ollama.api.OllamaChatOptions.builder()
                        .model("qwen2:7b")
                        .temperature(0.7)
                        .numCtx(2048)
                        .build())
                .build();
    }
}

package com.gunggeumap.ggm.question.service.impl;

import com.gunggeumap.ggm.question.service.ChatGptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class ChatGptServiceImpl implements ChatGptService {

  private final RestClient restClient;

  public ChatGptServiceImpl(
      @Value("${openai.api.key}") String apiKey,
      RestClient.Builder restClientBuilder
  ) {
    this.restClient = restClientBuilder
        .baseUrl("https://api.openai.com/v1")
        .defaultHeader("Authorization", "Bearer " + apiKey)
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  public String getChatCompletion(String prompt) {
    Map<String, Object> requestBody = Map.of(
        "model", "gpt-3.5-turbo",
        "messages", new Object[]{
            Map.of("role", "user", "content", prompt)
        }
    );

    try {
      return restClient.post()
          .uri("/chat/completions")
          .body(requestBody)
          .retrieve()
          .body(String.class);
    } catch (HttpClientErrorException e) {
      return "API 호출 실패: " + e.getMessage();
    }
  }
}

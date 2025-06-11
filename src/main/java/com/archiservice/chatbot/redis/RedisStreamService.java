package com.archiservice.chatbot.redis;

import com.archiservice.chatbot.dto.AiMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisStreamService {

  private final StreamOperations<String, Object, Object> streamOperations;

  public void sendToAI(AiMessage aiMessage) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(aiMessage);

      Map<String, Object> messageMap = Map.of(
          "data", json
      );

      streamOperations.add("ai-request-stream", messageMap);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("AiMessage 직렬화 실패", e);
    }
  }
}
package com.archiservice.chatbot.redis;

import com.archiservice.chatbot.dto.request.AiPromptMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisStreamService {

  private final StreamOperations<String, Object, Object> streamOperations;
  private final ObjectMapper objectMapper;

  public void sendToAI(AiPromptMessage aiPromptMessage) {
    try {
      Map<String, String> messageMap = new HashMap<>();
      messageMap.put("metadata", objectMapper.writeValueAsString(aiPromptMessage.getMetadata()));
      messageMap.put("payload", objectMapper.writeValueAsString(aiPromptMessage.getPayload()));

      RecordId recordId = streamOperations.add("ai-request-stream", messageMap);
      System.out.println("Redis Stream 전송 완료: " + recordId);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("AiMessage 직렬화 실패", e);
    }
  }
}
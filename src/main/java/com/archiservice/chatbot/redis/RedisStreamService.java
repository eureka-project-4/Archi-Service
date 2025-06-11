package com.archiservice.chatbot.redis;

import com.archiservice.chatbot.dto.ChatMessageDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisStreamService {

  private final StreamOperations<String, Object, Object> streamOperations;

  public void sendToAI(ChatMessageDto message) {
    Map<String, Object> messageMap = Map.of(
        "messageId", message.getMessageId(),
        "userId", message.getUserId(),
        "content", message.getContent(),
        "type", message.getType().toString(),
        "sender", message.getSender().toString(),
        "timestamp", message.getTimestamp().toString()
    );

    streamOperations.add("ai-request-stream", messageMap);
  }
}
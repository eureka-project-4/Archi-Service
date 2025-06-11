package com.archiservice.chatbot.service.impl;

import static com.archiservice.chatbot.dto.type.MessageType.KEYWORD_RECOMMENDATION;
import static com.archiservice.chatbot.dto.type.MessageType.PREFERENCE_UPDATE;
import static com.archiservice.chatbot.dto.type.MessageType.SUGGESTION;

import com.archiservice.chatbot.domain.Chat;
import com.archiservice.chatbot.dto.ChatMessageDto;
import com.archiservice.chatbot.dto.ChatResponseDto;
import com.archiservice.chatbot.dto.type.MessageType;
import com.archiservice.chatbot.dto.type.Sender;
import com.archiservice.chatbot.redis.RedisStreamService;
import com.archiservice.chatbot.repository.ChatRepository;
import com.archiservice.chatbot.service.AIService;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

  private final ChatRepository chatRepository;
  private final UserRepository userRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final RedisStreamService redisStreamService;

  @Override
  public void sendMessageToAI(Chat chat) {
    ChatMessageDto requestDto = ChatMessageDto.fromChat(chat);
    redisStreamService.sendToAI(requestDto);
  }

  @Override
  public void handleAIResponse(ChatResponseDto aiResponse) {
    // DB 저장
    User user = userRepository.findById(aiResponse.getUserId()).orElseThrow();
    Chat chat = Chat.builder()
        .user(user)
        .sender(Sender.BOT)
        .message(aiResponse.getContent())
        .messageType(aiResponse.getType())
        .build();
    chatRepository.save(chat);

    // 타입별 추가 처리
    switch(aiResponse.getType()) {
      case SUGGESTION:
        // bundleRecommendations 처리
        aiResponse.setContent("[추천] " + aiResponse.getContent());
        break;
      case KEYWORD_RECOMMENDATION:
        // extractedKeywords, bundleRecommendations 처리
        aiResponse.setContent("[키워드] " + aiResponse.getContent());
        break;
      case PREFERENCE_UPDATE:
        // userService.updatePreference(aiResponse.getUserId(), aiResponse.getUpdatedPreference());
        aiResponse.setContent("[성향 업데이트] " + aiResponse.getContent());
        break;
    }

    messagingTemplate.convertAndSendToUser(
        String.valueOf(aiResponse.getUserId()),
        "/queue/chat",
        aiResponse
    );
  }
}
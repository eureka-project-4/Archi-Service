package com.archiservice.chatbot.service.impl;


import com.archiservice.chatbot.domain.Chat;
import com.archiservice.chatbot.dto.ChatMessageDto;
import com.archiservice.chatbot.dto.type.MessageType;
import com.archiservice.chatbot.dto.type.Sender;
import com.archiservice.chatbot.repository.ChatRepository;
import com.archiservice.chatbot.service.AIService;
import com.archiservice.chatbot.service.ChatService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AIService aiService;

    @Override
    public void handleUserMessage(ChatMessageDto message, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Chat chat = Chat.builder()
            .user(user)
            .sender(Sender.USER)
            .message(message.getContent())
            .messageType(MessageType.USER_MESSAGE)
            .build();
        Chat savedChat = chatRepository.save(chat);

        String key = "chat:user:" + userId;
        redisTemplate.opsForList().leftPush(key, ChatMessageDto.fromChat(chat));
        redisTemplate.expire(key, Duration.ofHours(24));

        messagingTemplate.convertAndSendToUser(
            String.valueOf(userId),
            "/queue/chat",
            message
        );

        aiService.sendMessageToAI(savedChat);
    }

    @Override
    public List<ChatMessageDto> loadChatHistory(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Chat> chatPage = chatRepository.findByUser_UserId(userId, pageable);

        List<Chat> chats = new ArrayList<>(chatPage.getContent());
        chats.sort(Comparator.comparing(Chat::getCreatedAt));

        return chats.stream()
            .map(ChatMessageDto::fromChat)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteChatByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        chatRepository.deleteByUser_UserId(userId);
    }
}
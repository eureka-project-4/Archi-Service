package com.archiservice.chat.service.impl;

import com.archiservice.chat.domain.Chat;
import com.archiservice.chat.dto.MessageType;
import com.archiservice.chat.dto.response.ChatMessageDto;

import com.archiservice.chat.repository.ChatRepository;
import com.archiservice.chat.service.ChatService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Qualifier("chatCacheManager")
    private final CacheManager chatCacheManager;

    @Override
    public void handleUserMessage(ChatMessageDto message, User user) {
        Chat chat = Chat.builder()
                .user(user)
                .sender(Chat.Sender.USER)
                .message(message.getContent())
                .isValid(true)
                .isRecommend(false)
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
        messagingTemplate.convertAndSendToUser(
                user.getEmail(),
                "/queue/chat",
                message
        );

        sendBotResponse(user, message.getContent());
    }


    // 챗봇 응답 -> 클라이언트
    // Open api 연결하기
    @Override
    @Async
    public void sendBotResponse(User user, String userMessage) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String reply = "GPT 응답: \"" + userMessage;

        Chat chat = Chat.builder()
                .user(user)
                .sender(Chat.Sender.BOT)
                .message(reply)
                .isValid(true)
                .isRecommend(false)
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
        updateChatHistoryCache(user.getUserId());

        ChatMessageDto responseDto = ChatMessageDto.builder()
                .type(MessageType.TEXT)
                .sender("gpt-bot")
                .roomId(String.valueOf(user.getUserId()))
                .content(reply)
                .build();

//        ChatMessageDto responseDto = ChatMessageDto.builder()
//                .type(MessageType.SUGGESTION)
//                .sender("gpt-bot")
//                .roomId(String.valueOf(userId))
//                .content("다음 중 어떤 항목이 궁금하신가요?")
//                .options(List.of("갤럭시 S25", "로밍이용방법"))  // 버튼 텍스트 목록
//                .build();

        messagingTemplate.convertAndSendToUser(
                user.getEmail(),
                "/queue/chat",
                responseDto
        );
    }

    @Cacheable(value = "chatHistory", key = "#userId + ':' + #page + ':' + #size", condition = "#userId != null")
    @Override
    public List<ChatMessageDto> loadChatHistory(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Chat> chatPage = chatRepository.findByUserAndIsValidTrueOrderByCreatedAtDesc(user, pageable);

        List<Chat> chats = new ArrayList<>(chatPage.getContent());
        chats.sort(Comparator.comparing(Chat::getCreatedAt));

        return chats.stream()
                .map(chat -> ChatMessageDto.builder()
                        .type(MessageType.TEXT)
                        .sender(chat.getSender().name().toLowerCase())
                        .roomId(String.valueOf(user.getUserId()))
                        .content(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }



    @Override
    public void deleteChatByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        chatRepository.deleteByUser_UserId(userId);
    }

    // 캐시 갱신
    public void updateChatHistoryCache(Long userId) {
        if (userId == null) {
            log.warn("userId가 null");
            return;
        }

        List<ChatMessageDto> latest = fetchLatestChatFromDB(userId);

        Cache cache = chatCacheManager.getCache("chatHistory");
        if (cache != null) {
            String key = userId + ":0:30";
            cache.put(key, latest);
            log.info("캐시 직접 갱신됨 → {}", key);
        }
    }

    public List<ChatMessageDto> fetchLatestChatFromDB(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(0, 30, Sort.by("createdAt").descending());
        Page<Chat> chatPage = chatRepository.findByUserAndIsValidTrueOrderByCreatedAtDesc(user, pageable);

        List<Chat> chats = new ArrayList<>(chatPage.getContent());
        chats.sort(Comparator.comparing(Chat::getCreatedAt)); // 오름차순 정렬

        return chats.stream()
                .map(chat -> ChatMessageDto.builder()
                        .type(MessageType.TEXT)
                        .sender(chat.getSender().name().toLowerCase())
                        .roomId(String.valueOf(userId))
                        .content(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }



}


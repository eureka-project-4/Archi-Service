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

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final Long DUMMY_USER_ID = 1L; // 지금은 더미 유저 기준

    @Override
    public void handleUserMessage(ChatMessageDto message, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Chat chat = Chat.builder()
                .user(user)
                .sender(Chat.Sender.USER)
                .message(message.getContent())
                .isValid(true)
                .isRecommend(false)
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chat);

        messagingTemplate.convertAndSendToUser( // 사용자가 보낸 메시지 보여주기 위해
                String.valueOf(userId),
                "/queue/chat", // 구독 중인 경로
                message
        );

        sendBotResponse(userId, message.getContent());
    }

    // 챗봇 응답 -> 클라이언트
    // Open api 연결하기
    @Override
    @Async
    public void sendBotResponse(Long userId, String userMessage) {
        try {
            TimeUnit.SECONDS.sleep(1); // 응답 지연 시뮬레이션
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String reply = "GPT 응답: \"" + userMessage;

        User user = userRepository.findById(userId).orElseThrow();
        Chat chat = Chat.builder()
                .user(user)
                .sender(Chat.Sender.BOT)
                .message(reply)
                .isValid(true)
                .isRecommend(false)
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chat);

        // 클라이언트 전송용 DTO
//        ChatMessageDto responseDto = ChatMessageDto.builder()
//                .type(MessageType.TEXT)
//                .sender("gpt-bot")
//                .roomId(String.valueOf(userId))
//                .content(reply)
//                .build();

        ChatMessageDto responseDto = ChatMessageDto.builder()
                .type(MessageType.SUGGESTION)
                .sender("gpt-bot")
                .roomId(String.valueOf(userId))
                .content("다음 중 어떤 항목이 궁금하신가요?")
                .options(List.of("갤럭시 S25", "로밍이용방법"))  // 버튼 텍스트 목록
                .build();



        messagingTemplate.convertAndSendToUser(
                String.valueOf(userId), // 사용자 ID
                "/queue/chat",          // 사용자가 구독할 경로
                responseDto             // 보낼 메시지
        );
    }

    @Override
    public List<ChatMessageDto> loadChatHistory(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Chat> chatPage = chatRepository.findByUser_UserIdAndIsValidTrueOrderByCreatedAtDesc(userId, pageable);

        List<Chat> chats = new ArrayList<>(chatPage.getContent());

        // 오래된 순으로 정렬
        chats.sort(Comparator.comparing(Chat::getCreatedAt));

        return chats.stream()
                .map(chat -> ChatMessageDto.builder()
                        .type(MessageType.TEXT)
                        .sender(chat.getSender().name().toLowerCase())
                        .roomId(String.valueOf(userId))
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



}


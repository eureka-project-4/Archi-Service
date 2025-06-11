package com.archiservice.chatbot.service;


import com.archiservice.chatbot.domain.AuthInfo;
import com.archiservice.chatbot.dto.ChatMessageDto;
import java.util.List;

public interface ChatService {

    void handleUserMessage(ChatMessageDto message, AuthInfo authInfo);

    List<ChatMessageDto> loadChatHistory(Long userId, int page, int size);

    void deleteChatByUserId(Long userId);
}
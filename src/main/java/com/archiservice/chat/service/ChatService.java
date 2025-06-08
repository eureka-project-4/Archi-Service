package com.archiservice.chat.service;

import com.archiservice.chat.dto.response.ChatMessageDto;

import java.util.List;

public interface ChatService {

    void handleUserMessage(ChatMessageDto message, Long userId);
    void sendBotResponse(Long userId, String userMessage);
    List<ChatMessageDto> loadChatHistory(Long userId, int page, int size);
    void deleteChatByUserId(Long userId);

}

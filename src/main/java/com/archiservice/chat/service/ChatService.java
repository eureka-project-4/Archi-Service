package com.archiservice.chat.service;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.user.domain.User;

import java.util.List;

public interface ChatService {

    void handleUserMessage(ChatMessageDto message, User user);
    void sendBotResponse(User user, String userMessage);
    List<ChatMessageDto> loadChatHistory(Long userId, int page, int size);
    void deleteChatByUserId(Long userId);

}

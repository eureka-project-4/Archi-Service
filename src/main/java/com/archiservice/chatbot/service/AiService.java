package com.archiservice.chatbot.service;


import com.archiservice.chatbot.domain.AuthInfo;
import com.archiservice.chatbot.domain.Chat;
import com.archiservice.chatbot.dto.ChatResponseDto;

public interface AiService {

  void sendMessageToAI(Chat chat, AuthInfo authInfo);

  void handleAIResponse(ChatResponseDto aiResponse);
}
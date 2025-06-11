package com.archiservice.chatbot.service;


import com.archiservice.chatbot.domain.Chat;
import com.archiservice.chatbot.dto.ChatResponseDto;

public interface AIService {

  void sendMessageToAI(Chat chat);

  void handleAIResponse(ChatResponseDto aiResponse);
}
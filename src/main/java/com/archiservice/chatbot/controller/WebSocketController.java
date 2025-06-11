package com.archiservice.chatbot.controller;

import com.archiservice.chatbot.dto.ChatMessageDto;
import com.archiservice.chatbot.service.ChatService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessageDto message, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        chatService.handleUserMessage(message, userId);
    }
}
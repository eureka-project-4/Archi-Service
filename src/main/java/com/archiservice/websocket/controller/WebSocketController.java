package com.archiservice.websocket.controller;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatService chatService;

    // prefix ( "/app" )
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessageDto message , Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        chatService.handleUserMessage(message, userId);
    }
}

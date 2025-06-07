package com.archiservice.chat.controller;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDto message , Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        chatService.handleUserMessage(message, userId);
    }

}

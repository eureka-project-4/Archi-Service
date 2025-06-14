package com.archiservice.chatbot.controller;

import com.archiservice.chatbot.domain.AuthInfo;
import com.archiservice.chatbot.dto.request.ChatMessageRequestDto;
import com.archiservice.chatbot.service.ChatService;
import com.archiservice.common.security.CustomUser;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatService chatService;


    @MessageMapping("/chat/sendMessage")
    public void sendMessage(
            @Payload ChatMessageRequestDto message,
            Principal principal
    ) {
            AuthInfo authInfo = (AuthInfo) principal;
            chatService.handleUserMessage(message, authInfo);
    }
}
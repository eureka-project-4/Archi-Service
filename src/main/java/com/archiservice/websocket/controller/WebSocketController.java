package com.archiservice.websocket.controller;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.chat.service.ChatService;
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
    private final UserRepository userRepository;

    // prefix ( "/app" )
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessageDto message, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + email));

        chatService.handleUserMessage(message, user);
    }






}

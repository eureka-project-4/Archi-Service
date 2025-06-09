package com.archiservice.chat.controller;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.chat.service.ChatService;
import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ChatMessageDto>>> getChatHistory(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Long userId = customUser.getId();
        List<ChatMessageDto> data = chatService.loadChatHistory(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success("성공했습니다.", data));
    }



    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteChatHistory(@AuthenticationPrincipal CustomUser customUser) {
        Long userId = customUser.getId();
        chatService.deleteChatByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("성공했습니다.", "삭제 성공"));
    }



}

package com.archiservice.chat.controller;

import com.archiservice.chat.dto.response.ChatMessageDto;
import com.archiservice.chat.service.ChatService;
import com.archiservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<List<ChatMessageDto>>> getChatHistory(
            @PathVariable("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size ) {
        List<ChatMessageDto> data = chatService.loadChatHistory(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success("성공했습니다.", data));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteChatHistory(@PathVariable("userId") Long userId) {
        chatService.deleteChatByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("성공했습니다.", "삭제 성공"));
    }


}

package com.archiservice.chat.dto.response;

import com.archiservice.chat.dto.MessageType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private MessageType type;
    private String content;
    private String sender;
    private String roomId;

    // 선택지 응답 전용 필드
    private List<String> options;

}


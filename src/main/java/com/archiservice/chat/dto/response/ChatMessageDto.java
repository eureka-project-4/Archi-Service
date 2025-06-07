package com.archiservice.chat.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    private MessageType type;  // 메시지 타입 (CHAT, JOIN 등)
    private String content;    // 실제 메시지
    private String sender;     // 보낸 사람 ID 혹은 닉네임
    private String roomId;     // 채팅방 ID -> 유저 id랑 매핑

}


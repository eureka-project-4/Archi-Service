package com.archiservice.chat.dto.response;

import com.archiservice.chat.dto.MessageType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private MessageType type;
    private String content;
    private String sender;
    private String roomId;
    private LocalDateTime createdAt;

    // 선택지 응답 전용 필드
    private List<String> options;

}


package com.archiservice.chat.dto.response;

import com.archiservice.chat.dto.MessageType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private MessageType type;
    private String content;
    private String sender;
    private String roomId;

}


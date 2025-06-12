package com.archiservice.chatbot.dto.request;

import com.archiservice.chatbot.dto.ChatMessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiPromptMessage {
  private AuthMetadata metadata;
  private ChatMessageDto payload;
}

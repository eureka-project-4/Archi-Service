package com.archiservice.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiMessage {
  private AuthMetadata metadata;
  private ChatMessageDto payload;
}

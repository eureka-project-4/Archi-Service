package com.archiservice.chatbot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthMetadata {
  private Long tagCode;
  private String ageCode;
}
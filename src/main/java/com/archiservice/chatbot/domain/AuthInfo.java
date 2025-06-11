package com.archiservice.chatbot.domain;

import java.security.Principal;
import lombok.Getter;

@Getter
public class AuthInfo implements Principal {

  private final Long userId;
  private final int tagCode;
  private final int ageCode;

  private AuthInfo(Long userId, int tagCode, int ageCode) {
    this.userId = userId;
    this.tagCode = tagCode;
    this.ageCode = ageCode;
  }

  public static AuthInfo of(Long userId, int tagCode, int ageCode) {
    return new AuthInfo(userId, tagCode, ageCode);
  }

  @Override
  public String getName() {
    return String.valueOf(userId);
  }
}
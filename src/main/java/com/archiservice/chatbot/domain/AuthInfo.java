package com.archiservice.chatbot.domain;

import java.security.Principal;
import lombok.Getter;

@Getter
public class AuthInfo implements Principal {

  private final Long userId;
  private final String ageCode;
  private final Long tagCode;

  private AuthInfo(Long userId, String ageCode, Long tagCode) {
    this.userId = userId;
    this.tagCode = tagCode;
    this.ageCode = ageCode;
  }

  public static AuthInfo of(Long userId, String ageCode, Long tagCode) {
    return new AuthInfo(userId, ageCode ,tagCode);
  }

  @Override
  public String getName() {
    return String.valueOf(userId);
  }
}
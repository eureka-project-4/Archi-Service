package com.archiservice.user.dto.request;

import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {
    private String password;
    private String newPassword;
}

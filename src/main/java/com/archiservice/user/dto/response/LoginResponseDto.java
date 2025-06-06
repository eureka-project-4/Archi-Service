package com.archiservice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String message;
    private String username;
    private String accessToken;
    private String refreshToken;

    public static LoginResponseDto success(String username, String accessToken, String refreshToken) {
        return new LoginResponseDto("로그인이 완료되었습니다", username, accessToken, refreshToken);
    }
}

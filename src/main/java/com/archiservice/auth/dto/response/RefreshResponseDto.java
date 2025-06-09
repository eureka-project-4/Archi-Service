package com.archiservice.auth.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefreshResponseDto {
    private String email;
    private String newAccessToken;
}

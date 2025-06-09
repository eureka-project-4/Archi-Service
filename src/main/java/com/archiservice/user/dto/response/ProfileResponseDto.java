package com.archiservice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private String username;
    private String email;
    private String number;
    private String birth;
    private String gender;
    private LocalDateTime createdAt;
}

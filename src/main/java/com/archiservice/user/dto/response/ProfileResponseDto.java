package com.archiservice.user.dto.response;

import com.archiservice.user.domain.User;
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

    public static ProfileResponseDto from(User user) {
        return ProfileResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .number(user.getNumber())
                .birth(user.getBirth())
                .gender(user.getGender())
                .build();
    }
}

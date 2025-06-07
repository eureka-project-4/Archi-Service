package com.archiservice.auth.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
// 권한 테스트용 컨트롤러, 승인 이후 삭제 예정
public class TestController {

    // ROLE_USER 권한 확인 - 모든 인증된 사용자 접근 가능
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> userTest(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(200)
                        .codeName("SUCCESS")
                        .message("반갑습니다 " + customUser.getUsername() + "님! (USER 권한)")
                        .data(null)
                        .build()
        );
    }

    // ROLE_ADMIN 권한 확인 - ADMIN만 접근 가능
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> adminTest(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(200)
                        .codeName("SUCCESS")
                        .message("반갑습니다 " + customUser.getUsername() + "님! (ADMIN 권한)")
                        .data(null)
                        .build()
        );
    }

    // 인증만 필요 - 권한 상관없이 로그인만 되어있으면 접근 가능
    @GetMapping("/authenticated")
    public ResponseEntity<ApiResponse> authenticatedTest(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(200)
                        .codeName("SUCCESS")
                        .message("인증된 사용자: " + customUser.getUsername() + "님 (권한: " + customUser.getRole() + ")")
                        .data(null)
                        .build()
        );
    }

    // 토큰 없이 접근 가능 (테스트용)
    @GetMapping("/public")
    public ResponseEntity<ApiResponse> publicTest() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(200)
                        .codeName("SUCCESS")
                        .message("누구나 접근 가능한 API입니다")
                        .data(null)
                        .build()
        );
    }
}

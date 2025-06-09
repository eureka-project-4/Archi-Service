package com.archiservice.user.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.user.dto.request.PasswordUpdateRequestDto;
import com.archiservice.user.dto.response.ProfileResponseDto;
import com.archiservice.user.dto.response.TendencyResponseDto;
import com.archiservice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponseDto>> getUserProfile(@AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(userService.getUserProfile(user));
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(@Valid @RequestBody PasswordUpdateRequestDto request, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(userService.updatePassword(request,user));
    }

    @GetMapping("/tendency")
    public ResponseEntity<ApiResponse<List<TendencyResponseDto>>> getUserTendency(@AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(userService.getUserTendency(user));
    }
}

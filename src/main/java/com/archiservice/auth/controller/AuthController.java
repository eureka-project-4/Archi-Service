package com.archiservice.auth.controller;

import com.archiservice.auth.service.AuthServiceImpl;
import com.archiservice.common.response.CommonResponse;
import com.archiservice.user.dto.request.LoginRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse> refresh(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(authService.logout(accessToken));
    }

    @GetMapping("/hash/{password}")
    public String generateHash(@PathVariable String password) {
        return passwordEncoder.encode(password);
    }
}

package com.archiservice.auth.service;

import com.archiservice.common.enums.Result;
import com.archiservice.common.redis.RedisService;
import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.common.security.JwtUtil;
import com.archiservice.user.dto.request.LoginRequestDto;
import com.archiservice.user.dto.response.LoginResponseDto;
import com.archiservice.user.entity.User;
import com.archiservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    // 로그인
    public ApiResponse login(LoginRequestDto loginRequest) {
        try {
            // 1. 사용자 조회
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                return ApiResponse.builder()
                        .result(Result.FAIL)
                        .message("존재하지 않는 사용자입니다")
                        .build();
            }

            User user = userOpt.get();

            // 2. 비밀번호 검증
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ApiResponse.builder()
                        .result(Result.FAIL)
                        .message("비밀번호가 올바르지 않습니다")
                        .build();
            }

            // 3. CustomUser 생성 후 토큰 발급
            CustomUser customUser = new CustomUser(user);
            String accessToken = jwtUtil.generateAccessToken(customUser);
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            // 4. Refresh Token Redis에 저장
            redisService.saveRefreshToken(user.getEmail(), refreshToken);

            log.info("사용자 로그인 성공: {}", user.getEmail());

            // 5. 성공 응답
            LoginResponseDto responseDto = LoginResponseDto.success(
                    user.getEmail(), accessToken, refreshToken);

            return ApiResponse.builder()
                    .result(Result.SUCCESS)
                    .message("로그인이 완료되었습니다")
                    .data(responseDto)
                    .build();

        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ApiResponse.builder()
                    .result(Result.FAIL)
                    .message("로그인에 실패했습니다")
                    .build();
        }
    }

    // 토큰 재발급
    public ApiResponse refresh(String refreshTokenHeader) {
        try {
            // 1. Header에서 토큰 추출 (Bearer 제거)
            String refreshToken = refreshTokenHeader.replace("Bearer ", "");

            // 2. Refresh Token 유효성 검증
            if (!jwtUtil.validateToken(refreshToken)) {
                return ApiResponse.builder()
                        .result(Result.FAIL)
                        .message("유효하지 않은 Refresh Token입니다")
                        .build();
            }

            // 3. 토큰에서 username 추출
            String username = jwtUtil.extractUsername(refreshToken);

            // 4. Redis에서 저장된 Refresh Token과 비교
            if (!redisService.validateRefreshToken(username, refreshToken)) {
                return ApiResponse.builder()
                        .result(Result.FAIL)
                        .message("Refresh Token이 일치하지 않습니다")
                        .build();
            }

            // 5. 새로운 Access Token 발급
            Optional<User> userOpt = userRepository.findByEmail(username);
            if (userOpt.isEmpty()) {
                return ApiResponse.builder()
                        .result(Result.FAIL)
                        .message("존재하지 않는 사용자입니다")
                        .build();
            }

            CustomUser customUser = new CustomUser(userOpt.get());
            String newAccessToken = jwtUtil.generateAccessToken(customUser);

            log.info("Access Token 재발급 성공: {}", username);

            // 6. 새로운 Access Token만 반환
            LoginResponseDto responseDto = new LoginResponseDto(
                    "토큰 재발급이 완료되었습니다", username, newAccessToken, refreshToken);

            return ApiResponse.builder()
                    .result(Result.SUCCESS)
                    .message("토큰 재발급이 완료되었습니다")
                    .data(responseDto)
                    .build();

        } catch (Exception e) {
            log.error("토큰 재발급 실패: {}", e.getMessage());
            return ApiResponse.builder()
                    .result(Result.FAIL)
                    .message("토큰 재발급에 실패했습니다")
                    .build();
        }
    }

    // 로그아웃
    public ApiResponse logout(String accessTokenHeader) {
        try {
            // 1. Header에서 토큰 추출
            String accessToken = accessTokenHeader.replace("Bearer ", "");

            // 2. Access Token에서 username 추출
            String username = jwtUtil.extractUsername(accessToken);

            // 3. Redis에서 Refresh Token 삭제
            redisService.deleteRefreshToken(username);

            log.info("사용자 로그아웃: {}", username);

            return ApiResponse.builder()
                    .result(Result.SUCCESS)
                    .message("로그아웃이 완료되었습니다")
                    .build();

        } catch (Exception e) {
            log.error("로그아웃 실패: {}", e.getMessage());
            return ApiResponse.builder()
                    .result(Result.FAIL)
                    .message("로그아웃에 실패했습니다")
                    .build();
        }
    }
}

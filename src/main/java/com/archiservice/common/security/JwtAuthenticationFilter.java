package com.archiservice.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // "Bearer " 제거

            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                log.error("JWT 토큰에서 사용자명 추출 실패: {}", e.getMessage());
            }
        }

        // 2. 토큰이 유효하고 SecurityContext에 인증 정보가 없는 경우
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                // 3. UserDetails 로드
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // 4. 토큰 유효성 검증
                if (jwtUtil.validateToken(token, userDetails)) {

                    // 5. Authentication 객체 생성
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 6. SecurityContext에 인증 정보 설정
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.debug("JWT 인증 성공: {}", email);
                }

            } catch (Exception e) {
                log.error("JWT 인증 처리 중 오류: {}", e.getMessage());
            }
        }

        // 7. 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}

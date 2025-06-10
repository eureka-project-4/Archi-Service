package com.archiservice.common.config.handler;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.warn("인증되지 않은 사용자의 접근: {}, 에러: {}", request.getRequestURI(), authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> errorResponse = ApiResponse.fail(getErrorMessage(authException));

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private ErrorCode getErrorMessage(AuthenticationException authException) {
        if (authException.getMessage().contains("JWT expired")) {
            return ErrorCode.EXPIRED_TOKEN;
        } else if (authException.getMessage().contains("Invalid JWT")) {
            return ErrorCode.INVALID_TOKEN;
        } else {
            return ErrorCode.LOGIN_REQUIRED;
        }
    }

}


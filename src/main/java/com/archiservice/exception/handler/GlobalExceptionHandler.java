package com.archiservice.exception.handler;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(
      BusinessException e, HttpServletRequest request) {

    log.error("Business error at {}: {}", request.getRequestURI(), e.getMessage(), e);
    return ResponseEntity.ok(ApiResponse.fail(e.getErrorCode()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneralException(
      Exception e, HttpServletRequest request) {

    log.error("Unexpected error at {}: {}", request.getRequestURI(), e.getMessage(), e);
    return ResponseEntity.ok(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
  }
}
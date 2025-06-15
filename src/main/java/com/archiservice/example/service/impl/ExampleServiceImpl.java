package com.archiservice.example.service.impl;

import com.archiservice.example.dto.response.ExampleResponseDto;
import com.archiservice.example.service.ExampleService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements ExampleService {

  @Override
  public ExampleResponseDto getSuccessExample() {
    return new ExampleResponseDto(1L, "홍길동");
  }

  @Override
  public ExampleResponseDto getCustomErrorExample() {
    throw new BusinessException(ErrorCode.USER_NOT_FOUND);
  }

  @Override
  public ExampleResponseDto getUnhandledErrorExample() {
    String str = null;
    str.length(); // NPE 발생
    return null; // 도달하지 않음
  }
}
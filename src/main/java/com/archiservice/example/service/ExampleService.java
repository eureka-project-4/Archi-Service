package com.archiservice.example.service;

import com.archiservice.example.dto.response.ExampleResponseDto;

public interface ExampleService {
  ExampleResponseDto getSuccessExample();
  ExampleResponseDto getCustomErrorExample();
  ExampleResponseDto getUnhandledErrorExample();
}
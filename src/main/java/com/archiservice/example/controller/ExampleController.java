package com.archiservice.example.controller;


import com.archiservice.common.response.ApiResponse;
import com.archiservice.example.dto.response.ExampleResponseDto;
import com.archiservice.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleController {

  private final ExampleService exampleService;

  @GetMapping("/success")
  public ResponseEntity<ApiResponse<ExampleResponseDto>> getSuccess() {
    ExampleResponseDto data = exampleService.getSuccessExample();
    return ResponseEntity.ok(ApiResponse.success("성공했습니다.", data));
  }

  @GetMapping("/custom-error")
  public ResponseEntity<ApiResponse<ExampleResponseDto>> getCustomError() {
    ExampleResponseDto data = exampleService.getCustomErrorExample();
    return ResponseEntity.ok(ApiResponse.success("성공했습니다.", data));
  }

  @GetMapping("/unhandled-error")
  public ResponseEntity<ApiResponse<ExampleResponseDto>> getUnhandledError() {
    ExampleResponseDto data = exampleService.getUnhandledErrorExample();
    return ResponseEntity.ok(ApiResponse.success("성공했습니다.", data));
  }
}
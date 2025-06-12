package com.archiservice.recommend.controller;


import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.recommend.dto.response.RecommendResponseDto;
import com.archiservice.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping()
    public ResponseEntity<ApiResponse<RecommendResponseDto>> recommend(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(ApiResponse.success("조합 추천 성공", recommendService.recommend(customUser)));
    }

}

package com.archiservice.recommend.controller;


import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.recommend.dto.response.RecommendResponseDto;
import com.archiservice.recommend.service.RecommendService;
import com.archiservice.review.coupon.dto.response.CouponScoreResponseDto;
import com.archiservice.review.coupon.service.CouponReviewService;
import com.archiservice.review.plan.dto.response.PlanScoreResponseDto;
import com.archiservice.review.plan.service.PlanReviewService;
import com.archiservice.review.vas.dto.response.VasScoreResponseDto;
import com.archiservice.review.vas.service.VasReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    private final PlanReviewService planReviewService;
    private final VasReviewService vasReviewService;
    private final CouponReviewService couponReviewService;

    @GetMapping()
    public ResponseEntity<ApiResponse<RecommendResponseDto>> recommend(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(ApiResponse.success("조합 추천 성공", recommendService.recommend(customUser)));
    }

    @GetMapping("/plans/statistics")
    public ResponseEntity<ApiResponse<List<PlanScoreResponseDto>>> getPlanScoreStatistics() {
        List<PlanScoreResponseDto> statistics = planReviewService.getPlanScoreStatistics();
        return ResponseEntity.ok(ApiResponse.success("통계 조회", statistics));
    }

    @GetMapping("/vass/statistics")
    public ResponseEntity<ApiResponse<List<VasScoreResponseDto>>> getVasScoreStatistics() {
        List<VasScoreResponseDto> statistics = vasReviewService.getVasScoreStatistics();
        return ResponseEntity.ok(ApiResponse.success("통계 조회", statistics));
    }

    @GetMapping("/coupons/statistics")
    public ResponseEntity<ApiResponse<List<CouponScoreResponseDto>>> getCouponScoreStatistics() {
        List<CouponScoreResponseDto> statistics = couponReviewService.getCouponScoreStatistics();
        return ResponseEntity.ok(ApiResponse.success("통계 조회", statistics));
    }

}

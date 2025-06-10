package com.archiservice.review.plan.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.review.plan.dto.request.PlanReviewRequestDto;
import com.archiservice.review.plan.dto.response.PlanReviewResponseDto;
import com.archiservice.review.plan.service.PlanReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans/{planId}/reviews")
@RequiredArgsConstructor
public class PlanReviewController {

    private final PlanReviewService planReviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<PlanReviewResponseDto>> createReview(
            @PathVariable("planId") Long planId,
            @RequestBody @Valid PlanReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 작성에 성공하였습니다.", planReviewService.createReview(userId, planId, requestDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PlanReviewResponseDto>>> getReviews(
            @PathVariable("planId") Long planId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(direction), sort));
        Page<PlanReviewResponseDto> reviews = planReviewService.getReviewsByPlanId(planId, pageable);

        return ResponseEntity.ok(ApiResponse.success("리뷰 전체조회에 성공하였습니다.",reviews));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<PlanReviewResponseDto>> updateReview(
            @PathVariable("planId") Long planId,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody @Valid PlanReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 수정에 성공하였습니다.", planReviewService.updateReview(userId, reviewId, requestDto)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable("planId") Long planId,
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        planReviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok(ApiResponse.success("리뷰 삭제에 성공하였습니다.", null));
    }
}

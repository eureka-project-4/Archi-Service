package com.archiservice.review.plan.service;

import com.archiservice.review.plan.dto.response.PlanScoreResponseDto;
import com.archiservice.review.plan.dto.request.PlanReviewRequestDto;
import com.archiservice.review.plan.dto.response.PlanReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanReviewService {
    PlanReviewResponseDto createReview(Long userId, Long planId, PlanReviewRequestDto requestDto);
    PlanReviewResponseDto updateReview(Long userId, Long reviewId, PlanReviewRequestDto requestDto);
    void deleteReview(Long userId, Long reviewId);
    Page<PlanReviewResponseDto> getReviewsByPlanId(Long planId, Pageable pageable);

    List<PlanScoreResponseDto> getPlanScoreStatistics();
}

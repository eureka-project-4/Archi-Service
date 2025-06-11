package com.archiservice.review.plan.dto.response;

import com.archiservice.review.plan.domain.PlanReview;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PlanReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private String username;
    private Long planId;
    private String planName;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PlanReviewResponseDto from(PlanReview review) {
        return PlanReviewResponseDto.builder()
                .reviewId(review.getPlanReviewId())
                .userId(review.getUser().getUserId())
                .username(review.getUser().getUsername())
                .planId(review.getPlan().getPlanId())
                .planName(review.getPlan().getPlanName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}

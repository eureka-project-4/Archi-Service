package com.archiservice.review.plan.dto.response;

import lombok.Getter;

@Getter
public class PlanScoreResponseDto {
    private Long planId;
    private Double averageScore;
    private Integer reviewCount;

    public PlanScoreResponseDto(Long planId, Double averageScore, Integer reviewCount) {
        this.planId = planId;
        this.averageScore = averageScore != null ? Math.round(averageScore * 100.0) / 100.0 : 0.0;
        this.reviewCount = reviewCount;
    }
}
package com.archiservice.review.vas.dto.response;

import lombok.Getter;

@Getter
public class VasScoreResponseDto {
    private Long PlanId;
    private Double averageScore;
    private Integer reviewCount;

    public VasScoreResponseDto(Long planId, Double averageScore, Integer reviewCount) {
        PlanId = planId;
        this.averageScore = averageScore != null ? Math.round(averageScore * 100) / 100.0 : 0.0;
        this.reviewCount = reviewCount;
    }
}

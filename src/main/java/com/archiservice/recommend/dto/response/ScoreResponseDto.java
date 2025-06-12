package com.archiservice.recommend.dto.response;

import lombok.Getter;

@Getter
public class ScoreResponseDto {
    private Double averageScore;
    private Integer reviewCount;

    public ScoreResponseDto(Double averageScore, Integer reviewCount) {
        this.averageScore = averageScore != null ? Math.round(averageScore * 100) / 100.0 : 0.0;
        this.reviewCount = reviewCount;
    }
}

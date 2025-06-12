package com.archiservice.review.coupon.dto.response;

import lombok.Getter;

@Getter
public class CouponScoreResponseDto {
    private Long couponId;
    private Double averageScore;
    private Integer reviewCount;

    public CouponScoreResponseDto(Long couponId, Double averageScore, Integer reviewCount) {
        this.couponId = couponId;
        this.averageScore = averageScore != null ? Math.round(averageScore * 100.0) / 100.0 : 0.0;
        this.reviewCount = reviewCount;
    }
}

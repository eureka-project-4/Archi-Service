package com.archiservice.review.coupon.dto.response;

import com.archiservice.review.coupon.domain.CouponReview;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private String username;
    private Long couponId;
    private String couponName;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CouponReviewResponseDto from(CouponReview review) {
        return CouponReviewResponseDto.builder()
                .reviewId(review.getCouponReviewId())
                .userId(review.getUser().getUserId())
                .username(review.getUser().getUsername())
                .couponId(review.getCoupon().getCouponId())
                .couponName(review.getCoupon().getCouponName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}

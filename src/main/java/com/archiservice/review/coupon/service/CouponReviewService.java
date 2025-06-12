package com.archiservice.review.coupon.service;

import com.archiservice.recommend.dto.response.ScoreResponseDto;
import com.archiservice.review.coupon.dto.request.CouponReviewRequestDto;
import com.archiservice.review.coupon.dto.response.CouponReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CouponReviewService {
    CouponReviewResponseDto createReview(Long userId, Long couponId, CouponReviewRequestDto requestDto);
    CouponReviewResponseDto updateReview(Long userId, Long reviewId, CouponReviewRequestDto requestDto);
    void deleteReview(Long userId, Long reviewId);
    Page<CouponReviewResponseDto> getReviewsByCouponId(Long couponId, Pageable pageable);

    Map<Long, ScoreResponseDto> getCouponScoreStatistics();
    Integer getAverageReviewCountPerCouponAsInteger();
}


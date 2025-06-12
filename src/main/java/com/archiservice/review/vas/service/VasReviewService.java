package com.archiservice.review.vas.service;

import com.archiservice.recommend.dto.response.ScoreResponseDto;
import com.archiservice.review.vas.dto.request.VasReviewRequestDto;
import com.archiservice.review.vas.dto.response.VasReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface VasReviewService {
    VasReviewResponseDto createReview(Long userId, Long vasId, VasReviewRequestDto requestDto);
    VasReviewResponseDto updateReview(Long userId, Long reviewId, VasReviewRequestDto requestDto);
    void deleteReview(Long userId, Long reviewId);
    Page<VasReviewResponseDto> getReviewsByVasId(Long vasId, Pageable pageable);

    Map<Long, ScoreResponseDto> getVasScoreStatistics();
}

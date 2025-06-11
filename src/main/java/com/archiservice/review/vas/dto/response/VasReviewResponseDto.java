package com.archiservice.review.vas.dto.response;

import com.archiservice.review.vas.domain.VasReview;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VasReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private String username;
    private Long vasId;
    private String vasName;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VasReviewResponseDto from(VasReview review) {
        return VasReviewResponseDto.builder()
                .reviewId(review.getVasReviewId())
                .userId(review.getUser().getUserId())
                .username(review.getUser().getUsername())
                .vasId(review.getVas().getVasId())
                .vasName(review.getVas().getVasName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}

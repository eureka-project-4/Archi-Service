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
    private Long serviceId;
    private String serviceName;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VasReviewResponseDto from(VasReview review) {
        return VasReviewResponseDto.builder()
                .reviewId(review.getServiceReviewId())
                .userId(review.getUser().getUserId())
                .username(review.getUser().getUsername())
                .serviceId(review.getVas().getServiceId())
                .serviceName(review.getVas().getServiceName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}

package com.archiservice.review.plan.dto.request;

import com.archiservice.badword.NoBadWords;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanReviewRequestDto {

    @NotNull(message = "평점은 필수입니다")
    @Min(value = 1, message = "평점은 1점 이상이어야 합니다")
    @Max(value = 5, message = "평점은 5점 이하여야 합니다")
    private Integer score;

    @Size(max = 255, message = "리뷰 내용은 255자 이하여야 합니다")
    @NoBadWords
    private String content;

    @Builder
    public PlanReviewRequestDto(Integer score, String content) {
        this.score = score;
        this.content = content;
    }
}

package com.archiservice.product.bundle.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BundleDetailResponseDto {
    private Long bundleId;
    private long likeCount;
    private long dislikeCount;
    private long tagCode;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

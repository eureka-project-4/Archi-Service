package com.archiservice.review.vas.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.review.vas.dto.request.VasReviewRequestDto;
import com.archiservice.review.vas.dto.response.VasReviewResponseDto;
import com.archiservice.review.vas.service.VasReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vass/{vasId}/reviews")
@RequiredArgsConstructor
public class VasReviewController {

    private final VasReviewService vasReviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<VasReviewResponseDto>> createReview(
            @PathVariable("vasId") Long vasId,
            @RequestBody @Valid VasReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 작성에 성공하였습니다.",
                vasReviewService.createReview(userId, vasId, requestDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<VasReviewResponseDto>>> getReviews(
            @PathVariable("vasId") Long vasId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(direction), sort));
        Page<VasReviewResponseDto> reviews = vasReviewService.getReviewsByVasId(vasId, pageable);

        return ResponseEntity.ok(ApiResponse.success("리뷰 전체조회에 성공하였습니다.", reviews));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<VasReviewResponseDto>> updateReview(
            @PathVariable("vasId") Long vasId,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody @Valid VasReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 수정에 성공하였습니다.",
                vasReviewService.updateReview(userId, reviewId, requestDto)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable("vasId") Long vasId,
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        vasReviewService.deleteReview(userId, reviewId);

        return ResponseEntity.ok(ApiResponse.success("리뷰 삭제에 성공하였습니다.", null));
    }
}

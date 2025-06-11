package com.archiservice.review.coupon.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.review.coupon.dto.request.CouponReviewRequestDto;
import com.archiservice.review.coupon.dto.response.CouponReviewResponseDto;
import com.archiservice.review.coupon.service.CouponReviewService;
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
@RequestMapping("/coupons/{couponId}/reviews")
@RequiredArgsConstructor
public class CouponReviewController {

    private final CouponReviewService couponReviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<CouponReviewResponseDto>> createReview(
            @PathVariable("couponId") Long couponId,
            @RequestBody @Valid CouponReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 작성에 성공하였습니다.",
                couponReviewService.createReview(userId, couponId, requestDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CouponReviewResponseDto>>> getReviews(
            @PathVariable("couponId") Long couponId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(direction), sort));
        Page<CouponReviewResponseDto> reviews = couponReviewService.getReviewsByCouponId(couponId, pageable);

        return ResponseEntity.ok(ApiResponse.success("리뷰 전체조회에 성공하였습니다.", reviews));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<CouponReviewResponseDto>> updateReview(
            @PathVariable("couponId") Long couponId,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody @Valid CouponReviewRequestDto requestDto,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        return ResponseEntity.ok(ApiResponse.success("리뷰 수정에 성공하였습니다.",
                couponReviewService.updateReview(userId, reviewId, requestDto)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable("couponId") Long couponId,
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal CustomUser customUser) {

        Long userId = customUser.getId();
        couponReviewService.deleteReview(userId, reviewId);

        return ResponseEntity.ok(ApiResponse.success("리뷰 삭제에 성공하였습니다.", null));
    }
}

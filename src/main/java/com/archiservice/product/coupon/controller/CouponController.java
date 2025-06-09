package com.archiservice.product.coupon.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.dto.response.CouponResponseDto;
import com.archiservice.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CouponResponseDto>>> getAllCoupons() {
        return ResponseEntity.ok(ApiResponse.success(couponService.getAllCoupons()));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<ApiResponse<CouponDetailResponseDto>> getCouponDetail(@PathVariable("couponId") Long couponId) {
        return ResponseEntity.ok(ApiResponse.success(couponService.getCouponDetail(couponId)));
    }
}

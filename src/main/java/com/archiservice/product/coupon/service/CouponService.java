package com.archiservice.product.coupon.service;

import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.dto.response.CouponResponseDto;

import java.util.List;

public interface CouponService {
    List<CouponResponseDto> getAllCoupons();
    CouponDetailResponseDto getCouponDetail(Long couponId);
}

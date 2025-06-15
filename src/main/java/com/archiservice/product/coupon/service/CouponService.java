package com.archiservice.product.coupon.service;

import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.dto.response.CouponResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponService {
    Page<CouponResponseDto> getAllCoupons(Pageable pageable);
    CouponDetailResponseDto getCouponDetail(Long couponId);
}

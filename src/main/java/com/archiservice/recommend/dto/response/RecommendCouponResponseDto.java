package com.archiservice.recommend.dto.response;

import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendCouponResponseDto {
    List<CouponDetailResponseDto> coupons;
}

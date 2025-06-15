package com.archiservice.product.bundle.dto.response;

import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BundleCombinationResponseDto {

    BundleDetailResponseDto bundleDetail;
    PlanDetailResponseDto planDetail;
    VasDetailResponseDto vasDetail;
    CouponDetailResponseDto couponDetail;

    public static BundleCombinationResponseDto from(BundleDetailResponseDto bundleDto, PlanDetailResponseDto planDto, VasDetailResponseDto vasDto, CouponDetailResponseDto couponDto) {
        return BundleCombinationResponseDto.builder()
                .bundleDetail(bundleDto)
                .planDetail(planDto)
                .vasDetail(vasDto)
                .couponDetail(couponDto)
                .build();
    }
}

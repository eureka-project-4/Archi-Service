package com.archiservice.user.service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.vas.dto.response.VASDetailResponseDto;
import com.archiservice.user.dto.request.ReservationRequestDto;
import com.archiservice.user.dto.response.ContractDetailResponseDto;
import com.archiservice.user.enums.Period;

public interface ContractsService {
    ApiResponse<PlanDetailResponseDto> getPlan(Period period, CustomUser customUser);
    ApiResponse<VASDetailResponseDto> getService(Period period, CustomUser customUser);
    ApiResponse<CouponDetailResponseDto> getCoupon(Period period, CustomUser customUser);
    ApiResponse<ContractDetailResponseDto> getContract (Period period, CustomUser customUser);

    ApiResponse updateNextReservation(ReservationRequestDto request, CustomUser customUser);
}

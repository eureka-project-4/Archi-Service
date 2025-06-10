package com.archiservice.user.service.impl;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.exception.business.UserNotFoundException;
import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.service.CouponService;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.service.PlanService;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.service.VasService;
import com.archiservice.user.domain.User;
import com.archiservice.user.dto.request.ReservationRequestDto;
import com.archiservice.user.dto.response.ContractDetailResponseDto;
import com.archiservice.user.dto.response.ContractOnlyResponseDto;
import com.archiservice.user.enums.Period;
import com.archiservice.user.repository.ContractRepository;
import com.archiservice.user.repository.UserRepository;
import com.archiservice.user.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final PlanService planService;
    private final VasService vasService;
    private final CouponService couponService;

    @Override
    public ApiResponse<PlanDetailResponseDto> getPlan(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long planId = contractRepository.findPlanIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        PlanDetailResponseDto planDetailResponseDto = planService.getPlanDetail(planId);

        return ApiResponse.success(planDetailResponseDto);
    }

    @Override
    public ApiResponse<VasDetailResponseDto> getVas(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long vasId = contractRepository.findVasIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        VasDetailResponseDto vasDetailResponseDto = vasService.getVASDetail(vasId);

        return ApiResponse.success(vasDetailResponseDto);
    }

    @Override
    public ApiResponse<CouponDetailResponseDto> getCoupon(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long couponId = contractRepository.findCouponIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        CouponDetailResponseDto couponDetailResponseDto = couponService.getCouponDetail(couponId);

        return ApiResponse.success(couponDetailResponseDto);
    }

    @Override
    public ApiResponse<ContractDetailResponseDto> getContract(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long planId = contractRepository.findPlanIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        PlanDetailResponseDto planDetailResponseDto = planService.getPlanDetail(planId);

        Long vasId = contractRepository.findVasIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        VasDetailResponseDto vasDetailResponseDto = vasService.getVASDetail(vasId);

        Long couponId = contractRepository.findCouponIdByOffset(user.getUserId(), period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        CouponDetailResponseDto couponDetailResponseDto = couponService.getCouponDetail(couponId);

        ContractOnlyResponseDto contractOnlyResponseDto = contractRepository.findContractByOffset(user, period.getOffset())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        ContractDetailResponseDto contractDetailResponseDto = ContractDetailResponseDto.from(contractOnlyResponseDto, planDetailResponseDto, vasDetailResponseDto, couponDetailResponseDto);

        return ApiResponse.success(contractDetailResponseDto);
    }

    // 바로 이어서 작업할 부분
    @Override
    public ApiResponse updateNextReservation(ReservationRequestDto request, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());
        return null;
    }
}

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
import com.archiservice.product.vas.dto.response.VASDetailResponseDto;
import com.archiservice.product.vas.service.VASService;
import com.archiservice.user.domain.User;
import com.archiservice.user.dto.request.ReservationRequestDto;
import com.archiservice.user.dto.response.ContractDetailResponseDto;
import com.archiservice.user.dto.response.ContractOnlyResponseDto;
import com.archiservice.user.enums.Period;
import com.archiservice.user.repository.ContractsRepository;
import com.archiservice.user.repository.UserRepository;
import com.archiservice.user.service.ContractsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractsServiceImpl implements ContractsService {
    private final UserRepository userRepository;
    private final ContractsRepository contractsRepository;
    private final PlanService planService;
    private final VASService vasService;
    private final CouponService couponService;

    @Override
    public ApiResponse<PlanDetailResponseDto> getPlan(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long planId = contractsRepository.findPlanIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        PlanDetailResponseDto planDetailResponseDto = planService.getPlanDetail(planId);

        return ApiResponse.success(planDetailResponseDto);
    }

    @Override
    public ApiResponse<VASDetailResponseDto> getService(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long vasId = contractsRepository.findVasIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        VASDetailResponseDto vasDetailResponseDto = vasService.getVASDetail(vasId);

        return ApiResponse.success(vasDetailResponseDto);
    }

    @Override
    public ApiResponse<CouponDetailResponseDto> getCoupon(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long couponId = contractsRepository.findCouponIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        CouponDetailResponseDto couponDetailResponseDto = couponService.getCouponDetail(couponId);

        return ApiResponse.success(couponDetailResponseDto);
    }

    @Transactional
    @Override
    public ApiResponse<ContractDetailResponseDto> getContract(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long planId = contractsRepository.findPlanIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        PlanDetailResponseDto planDetailResponseDto = planService.getPlanDetail(planId);

        Long vasId = contractsRepository.findVasIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        VASDetailResponseDto vasDetailResponseDto = vasService.getVASDetail(vasId);

        Long couponId = contractsRepository.findCouponIdByPeriod(user.getUserId(), period == Period.CURRENT)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        CouponDetailResponseDto couponDetailResponseDto = couponService.getCouponDetail(couponId);

        ContractOnlyResponseDto contractOnlyResponseDto = contractsRepository.findContractByPeriod(user, period == Period.CURRENT)
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

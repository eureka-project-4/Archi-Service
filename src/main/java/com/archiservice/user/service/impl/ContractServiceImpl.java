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
import com.archiservice.user.domain.Contract;
import com.archiservice.user.domain.User;
import com.archiservice.user.dto.response.ContractDetailResponseDto;
import com.archiservice.user.enums.Period;
import com.archiservice.user.repository.ContractRepository;
import com.archiservice.user.repository.UserRepository;
import com.archiservice.user.repository.custom.ContractCustomRepository;
import com.archiservice.user.service.ContractService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final ContractCustomRepository contractCustomRepository;
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
    public ApiResponse<List<ContractDetailResponseDto>> getContract(Period period, CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        List<ContractDetailResponseDto> contractDetailResponseListDto = contractCustomRepository.findContractByOffset(user, period);

        return ApiResponse.success(contractDetailResponseListDto);
    }

    @Override
    @Transactional
    public ApiResponse cancelNextReservation(CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new UserNotFoundException());

        List<Contract> contractList = contractRepository.findTop2ByUserOrderByIdDesc(user);
        Contract newContract = contractList.get(0);
        Contract curContract = contractList.get(1);

        newContract.copyFrom(curContract);

        return ApiResponse.success(null);
    }
}

package com.archiservice.user.dto.response;

import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ContractDetailResponseDto {
    private String paymentMethod;
    private long contractPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String planName;
    private int planPrice;
    private String planCategory;
    private int monthData;
    private String callUsage;
    private String messageUsage;

    private String vasName;
    private int vasPrice;
    private String vasCategory;
    private String vasDescription;
    private int saleRate;

    private String couponName;
    private int couponPrice;
    private String couponCategory;

    public static ContractDetailResponseDto from(ContractOnlyResponseDto contractDto, PlanDetailResponseDto planDto, VasDetailResponseDto vasDto, CouponDetailResponseDto couponDto) {
        return ContractDetailResponseDto.builder()
                .paymentMethod(contractDto.getPaymentMethod())
                .contractPrice(contractDto.getPrice())
                .startDate(contractDto.getStartDate())
                .endDate(contractDto.getEndDate())

                .planName(planDto.getPlanName())
                .planPrice(planDto.getPrice())
                .planCategory(planDto.getCategory())
                .monthData(planDto.getMonthData())
                .callUsage(planDto.getCallUsage())
                .messageUsage(planDto.getMessageUsage())

                .vasName(vasDto.getVasName())
                .vasPrice(vasDto.getPrice())
                .vasCategory(vasDto.getCategory())
                .vasDescription(vasDto.getVasDescription())
                .saleRate(vasDto.getSaleRate())

                .couponName(couponDto.getCouponName())
                .couponPrice(couponDto.getPrice())
                .couponCategory(couponDto.getCategory())
                .build();
    }
}

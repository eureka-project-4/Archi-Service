package com.archiservice.product.plan.dto.response;

import com.archiservice.product.plan.domain.Plan;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class PlanDetailResponseDto {
    private Long planId;
    private String planName;
    private Integer price;
    private Integer monthData;
    private String callUsage;
    private String messageUsage;
    private String benefit;

    /*
    TODO: category, targetAge 는 공통코드에서 가져와 문자로 변환
     */
    private List<String> tags;
    private String category;
    private String targetAge; // 가입 대상 연령


    public static PlanDetailResponseDto from(Plan plan, List<String> tags) {
        return PlanDetailResponseDto.builder()
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .price(plan.getPrice())
                .monthData(plan.getMonthData())
                .callUsage(plan.getCallUsage())
                .messageUsage(plan.getMessageUsage())
                .benefit(plan.getBenefit())
                .tags(tags)
                .category(plan.getCategoryCode())
                .targetAge(plan.getAgeCode())
                .build();
    }
}

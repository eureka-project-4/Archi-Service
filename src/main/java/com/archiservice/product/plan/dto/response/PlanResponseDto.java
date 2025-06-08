package com.archiservice.product.plan.dto.response;

import com.archiservice.product.plan.domain.Plan;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class PlanResponseDto {
    private Long planId;
    private String planName;
    private Integer price;
    /*
    TODO: category, targetAge 는 공통코드에서 가져와 문자로 변환
     */
    private List<String> tags;
    private String category;
    private String targetAge; // 가입 대상 연령

    public static PlanResponseDto from(Plan plan, List<String> tags){
        return PlanResponseDto.builder()
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .price(plan.getPrice())
                .tags(tags)
                .category(plan.getCategoryCode())
                .targetAge(plan.getAgeCode())
                .build();
    }
}

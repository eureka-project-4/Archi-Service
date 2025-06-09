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
    private List<String> tags;
    private String category;
    private String targetAge;

    public static PlanResponseDto from(Plan plan, List<String> tags, String category, String targetAge) {
        return PlanResponseDto.builder()
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .price(plan.getPrice())
                .tags(tags)
                .category(category)
                .targetAge(targetAge)
                .build();
    }
}

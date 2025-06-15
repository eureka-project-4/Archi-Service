package com.archiservice.recommend.dto.response;

import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendPlanResponseDto {
    List<PlanDetailResponseDto> plans;
}

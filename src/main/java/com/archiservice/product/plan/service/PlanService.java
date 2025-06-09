package com.archiservice.product.plan.service;

import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanResponseDto;

import java.util.List;

public interface PlanService {
    List<PlanResponseDto> getAllPlans();
    PlanDetailResponseDto getPlanDetail(Long planId);
}

package com.archiservice.product.plan.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanResponseDto;
import com.archiservice.product.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PlanResponseDto>>> getAllPlans() {
        return ResponseEntity.ok(ApiResponse.success(planService.getAllPlans()));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<PlanDetailResponseDto>> getPlanDetail(@PathVariable("planId") Long planId) {
        return ResponseEntity.ok(ApiResponse.success(planService.getPlanDetail(planId)));
    }

}

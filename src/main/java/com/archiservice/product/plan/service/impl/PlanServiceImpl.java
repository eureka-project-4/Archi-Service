package com.archiservice.product.plan.service.impl;

import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanResponseDto;
import com.archiservice.product.plan.repository.PlanRepository;
import com.archiservice.product.plan.service.PlanService;
import com.archiservice.tagmeta.service.TagMetaService;
import com.archiservice.tagmeta.service.impl.TagMetaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final TagMetaService tagMetaService;

    @Override
    public List<PlanResponseDto> getAllPlans() {
        return planRepository.findAll().stream()
                .map(plan -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(plan.getTagCode());
                    return PlanResponseDto.from(plan, tags);
                })
                .toList();
    }

    @Override
    public PlanDetailResponseDto getPlanDetail(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> tags = tagMetaService.extractTagsFromCode(plan.getTagCode());

        return PlanDetailResponseDto.from(plan, tags);
    }
}

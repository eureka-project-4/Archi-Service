package com.archiservice.product.plan.service.impl;

import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanResponseDto;
import com.archiservice.product.plan.repository.PlanRepository;
import com.archiservice.product.plan.service.PlanService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final TagMetaService tagMetaService;
    private final CommonCodeService commonCodeService;

    public static final String CATEGORY_GROUP_CODE = "G02";
    public static final String AGE_GROUP_CODE = "G01";

    @Override
    public Page<PlanResponseDto> getAllPlans(Pageable pageable) {
        Page<Plan> planPage = planRepository.findAll(pageable);

        return planPage.map(plan -> {
            List<String> tags = tagMetaService.extractTagsFromCode(plan.getTagCode());
            String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, plan.getCategoryCode());
            String targetAge = commonCodeService.getCodeName(AGE_GROUP_CODE, plan.getAgeCode());
            return PlanResponseDto.from(plan, tags, category, targetAge);
        });
    }

    @Override
    public PlanDetailResponseDto getPlanDetail(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> tags = tagMetaService.extractTagsFromCode(plan.getTagCode());
        String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, plan.getCategoryCode());
        String targetAge = commonCodeService.getCodeName(AGE_GROUP_CODE, plan.getAgeCode());

        return PlanDetailResponseDto.from(plan, tags, category, targetAge);
    }
}

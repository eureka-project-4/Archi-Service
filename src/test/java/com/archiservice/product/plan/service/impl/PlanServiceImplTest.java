package com.archiservice.product.plan.service.impl;

import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import com.archiservice.exception.BusinessException;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.dto.response.PlanResponseDto;
import com.archiservice.product.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock PlanRepository planRepository;

    @InjectMocks PlanServiceImpl planService;


    @DisplayName("요금제 목록 조회 서비스 - 빈 리스트 반환")
    @Test
    void getAllPlans_EmptyList_OK() {
        when(planRepository.findAll()).thenReturn(Collections.emptyList());

        List<PlanResponseDto> result = planService.getAllPlans();

        assertTrue(result.isEmpty());
    }


    @DisplayName("요금제 상세 조회 서비스 - 존재하지않는 ID")
    @Test
    void getPlanDetail_NOT_FOUND_ID() {
        when(planRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> planService.getPlanDetail(99L));
    }
}


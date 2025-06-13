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
    @Mock TagMetaService tagMetaService;
    @Mock CommonCodeService commonCodeService;

    @InjectMocks PlanServiceImpl planService;


//    @DisplayName("요금제 목록 조회 서비스 - Plan 리스트 정상 변환")
//    @Test
//    void getAllPlans_OK() {
//        // given
//        Plan plan = Plan.builder()
//                .planName("요금제A")
//                .price(10000)
//                .monthData(5)
//                .callUsage("100분")
//                .messageUsage("무제한")
//                .benefit("혜택")
//                .tagCode(101L)
//                .ageCode("A01")
//                .categoryCode("C01")
//                .build();
//
//
//        when(planRepository.findAll()).thenReturn(List.of(plan));
//        when(tagMetaService.extractTagsFromCode(101L)).thenReturn(List.of("운동"));
//        when(commonCodeService.getCodeName("G02", "C01")).thenReturn("헬스");
//        when(commonCodeService.getCodeName("G01", "A01")).thenReturn("20대");
//
//        // when
//        List<PlanResponseDto> result = planService.getAllPlans();
//
//        // then
//        assertEquals(1, result.size());
//        PlanResponseDto dto = result.get(0);
//        assertEquals("헬스", dto.getCategory());
//        assertEquals("20대", dto.getTargetAge());
//        assertEquals(List.of("운동"), dto.getTags());
//    }

//    @DisplayName("요금제 목록 조회 서비스 - 빈 리스트 반환")
//    @Test
//    void getAllPlans_EmptyList_OK() {
//        when(planRepository.findAll()).thenReturn(Collections.emptyList());
//
//        List<PlanResponseDto> result = planService.getAllPlans();
//
//        assertTrue(result.isEmpty());
//    }

    @DisplayName("요금제 상세 조회 서비스 - 정상 응답")
    @Test
    void getPlanDetail_OK() {
        // given
        Plan plan = Plan.builder()
                .planName("요금제A")
                .price(10000)
                .monthData(5)
                .callUsage("100분")
                .messageUsage("무제한")
                .benefit("혜택")
                .tagCode(101L)
                .ageCode("A01")
                .categoryCode("C01")
                .build();


        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(tagMetaService.extractTagsFromCode(101L)).thenReturn(List.of("요가"));
        when(commonCodeService.getCodeName("G02", "C01")).thenReturn("운동");
        when(commonCodeService.getCodeName("G01", "A01")).thenReturn("30대");

        // when
        PlanDetailResponseDto result = planService.getPlanDetail(1L);

        // then
        assertEquals("운동", result.getCategory());
        assertEquals("30대", result.getTargetAge());
        assertEquals(List.of("요가"), result.getTags());
    }

    @DisplayName("요금제 상세 조회 서비스 - 존재하지않는 ID")
    @Test
    void getPlanDetail_NOT_FOUND_ID() {
        when(planRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> planService.getPlanDetail(99L));
    }
}


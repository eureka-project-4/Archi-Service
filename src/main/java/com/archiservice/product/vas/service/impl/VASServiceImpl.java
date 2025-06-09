package com.archiservice.product.vas.service.impl;

import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.product.vas.domain.VAS;
import com.archiservice.product.vas.dto.response.VASDetailResponseDto;
import com.archiservice.product.vas.dto.response.VASResponseDto;
import com.archiservice.product.vas.repository.VASRepository;
import com.archiservice.product.vas.service.VASService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VASServiceImpl implements VASService {

    private final VASRepository vasRepository;
    private final TagMetaService tagMetaService;
    private final CommonCodeService commonCodeService;

    public static final String CATEGORY_GROUP_CODE = "G03"; // 부가 서비스

    @Override
    public List<VASResponseDto> getAllVASs() {
        return vasRepository.findAll().stream()
                .map(vas -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(vas.getTagCode());
                    String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, vas.getCategoryCode());
                    return VASResponseDto.from(vas, tags, category);
                })
                .toList();
    }

    @Override
    public VASDetailResponseDto getVASDetail(Long serviceId) {
        VAS vas = vasRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> tags = tagMetaService.extractTagsFromCode(vas.getTagCode());
        String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, vas.getCategoryCode());

        return VASDetailResponseDto.from(vas, tags, category);
    }
}

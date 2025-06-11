package com.archiservice.product.vas.service.impl;

import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.product.vas.domain.Vas;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasResponseDto;
import com.archiservice.product.vas.repository.VasRepository;
import com.archiservice.product.vas.service.VasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VasServiceImpl implements VasService {

    private final VasRepository vasRepository;
    private final TagMetaService tagMetaService;
    private final CommonCodeService commonCodeService;

    public static final String CATEGORY_GROUP_CODE = "G03"; // 부가 서비스

    @Override
    public List<VasResponseDto> getAllVas() {
        return vasRepository.findAll().stream()
                .map(vas -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(vas.getTagCode());
                    String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, vas.getCategoryCode());
                    return VasResponseDto.from(vas, tags, category);
                })
                .toList();
    }

    @Override
    public VasDetailResponseDto getVasDetail(Long vasId) {
        Vas vas = vasRepository.findById(vasId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> tags = tagMetaService.extractTagsFromCode(vas.getTagCode());
        String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, vas.getCategoryCode());

        return VasDetailResponseDto.from(vas, tags, category);
    }
}

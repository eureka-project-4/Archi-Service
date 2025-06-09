package com.archiservice.code.commoncode.service.impl;

import com.archiservice.code.commoncode.domain.CommonCode;
import com.archiservice.code.commoncode.repository.CommonCodeRepository;
import com.archiservice.code.commoncode.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonCodeServiceImpl implements CommonCodeService {
    private final CommonCodeRepository commonCodeRepository;

    @Override
    public String getCodeName(String groupCode, String commonCode) {
        return commonCodeRepository.findByGroupCodeAndCommonCode(groupCode, commonCode)
                .map(CommonCode::getCommonName)
                .orElse(commonCode);
    }

}

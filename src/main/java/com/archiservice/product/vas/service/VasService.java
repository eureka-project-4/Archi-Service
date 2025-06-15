package com.archiservice.product.vas.service;

import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VasService {
    Page<VasResponseDto> getAllVas(Pageable pageable);
    VasDetailResponseDto getVasDetail(Long vasId);
}

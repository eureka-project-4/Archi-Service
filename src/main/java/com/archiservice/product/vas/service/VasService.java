package com.archiservice.product.vas.service;

import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasResponseDto;

import java.util.List;

public interface VasService {
    List<VasResponseDto> getAllVas();
    VasDetailResponseDto getVasDetail(Long vasId);
}

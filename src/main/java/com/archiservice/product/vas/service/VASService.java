package com.archiservice.product.vas.service;

import com.archiservice.product.vas.dto.response.VASDetailResponseDto;
import com.archiservice.product.vas.dto.response.VASResponseDto;

import java.util.List;

public interface VASService {
    List<VASResponseDto> getAllVASs();
    VASDetailResponseDto getVASDetail(Long serviceId);
}

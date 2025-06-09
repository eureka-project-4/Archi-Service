package com.archiservice.product.vas.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.product.vas.dto.response.VASDetailResponseDto;
import com.archiservice.product.vas.dto.response.VASResponseDto;
import com.archiservice.product.vas.service.VASService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vass")
@RequiredArgsConstructor
public class VASController {

    private final VASService vasService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VASResponseDto>>> getAllVASs() {
        return ResponseEntity.ok(ApiResponse.success(vasService.getAllVASs()));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<VASDetailResponseDto>> getVASDetail(@PathVariable("serviceId") Long serviceId) {
        return ResponseEntity.ok(ApiResponse.success(vasService.getVASDetail(serviceId)));
    }
}

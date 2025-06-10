package com.archiservice.product.vas.controller;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.dto.response.VasResponseDto;
import com.archiservice.product.vas.service.VasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vass")
@RequiredArgsConstructor
public class VasController {

    private final VasService vasService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VasResponseDto>>> getAllVASs() {
        return ResponseEntity.ok(ApiResponse.success(vasService.getAllVASs()));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<VasDetailResponseDto>> getVASDetail(@PathVariable("serviceId") Long serviceId) {
        return ResponseEntity.ok(ApiResponse.success(vasService.getVASDetail(serviceId)));
    }
}

package com.archiservice.recommend.dto.response;

import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendVasResponseDto {
    List<VasDetailResponseDto> vass;
}
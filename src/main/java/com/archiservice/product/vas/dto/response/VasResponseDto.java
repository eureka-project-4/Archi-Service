package com.archiservice.product.vas.dto.response;

import com.archiservice.product.vas.domain.Vas;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VasResponseDto {
    private Long serviceId;
    private String serviceName;
    private Integer price;
    private Integer discountedPrice;
    private Integer saleRate;
    private String imageUrl;
    private List<String> tags;
    private String category;

    public static VasResponseDto from(Vas vas, List<String> tags, String category) {
        return VasResponseDto.builder()
                .serviceId(vas.getServiceId())
                .serviceName(vas.getServiceName())
                .price(vas.getPrice())
                .discountedPrice(vas.getDiscountedPrice())
                .saleRate(vas.getSaleRate())
                .imageUrl(vas.getImageUrl())
                .tags(tags)
                .category(category)
                .build();
    }
}


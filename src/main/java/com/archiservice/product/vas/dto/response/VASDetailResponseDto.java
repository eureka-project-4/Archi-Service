package com.archiservice.product.vas.dto.response;

import com.archiservice.product.vas.domain.VAS;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class VASDetailResponseDto {
    private Long serviceId;
    private String serviceName;
    private Integer price;
    private Integer discountedPrice;
    private Integer saleRate;
    private String imageUrl;
    private String serviceDescription;
    private List<String> tags;
    private String category;
    private boolean isOnSale;

    public static VASDetailResponseDto from(VAS vas, List<String> tags, String category) {
        return VASDetailResponseDto.builder()
                .serviceId(vas.getServiceId())
                .serviceName(vas.getServiceName())
                .price(vas.getPrice())
                .discountedPrice(vas.getDiscountedPrice())
                .saleRate(vas.getSaleRate())
                .imageUrl(vas.getImageUrl())
                .serviceDescription(vas.getServiceDescription())
                .tags(tags)
                .category(category)
                .isOnSale(vas.isOnSale())
                .build();
    }
}

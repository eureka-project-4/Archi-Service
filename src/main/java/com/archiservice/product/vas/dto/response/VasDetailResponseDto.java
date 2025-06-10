package com.archiservice.product.vas.dto.response;

import com.archiservice.product.vas.domain.Vas;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class VasDetailResponseDto {
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

    public static VasDetailResponseDto from(Vas vas, List<String> tags, String category) {
        return VasDetailResponseDto.builder()
                .serviceId(vas.getVasId())
                .serviceName(vas.getVasName())
                .price(vas.getPrice())
                .discountedPrice(vas.getDiscountedPrice())
                .saleRate(vas.getSaleRate())
                .imageUrl(vas.getImageUrl())
                .serviceDescription(vas.getVasDescription())
                .tags(tags)
                .category(category)
                .isOnSale(vas.isOnSale())
                .build();
    }
}

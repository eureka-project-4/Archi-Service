package com.archiservice.user.dto.request;

import com.archiservice.product.bundle.domain.ProductBundle;
import lombok.Getter;

@Getter
public class CreateContractRequestDto {
    private ProductBundle bundle;
    private Long price;
}

package com.archiservice.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ContractOnlyResponseDto {
    private String paymentMethod;
    private long price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

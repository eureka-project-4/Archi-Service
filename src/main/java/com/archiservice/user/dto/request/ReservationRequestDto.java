package com.archiservice.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRequestDto {
    private long bundleId;
    private long price;
}

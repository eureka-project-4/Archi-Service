package com.archiservice.user.repository.custom;

import com.archiservice.user.domain.User;
import com.archiservice.user.dto.response.ContractDetailResponseDto;
import com.archiservice.user.enums.Period;

import java.util.List;

public interface ContractCustomRepository {
    List<ContractDetailResponseDto> findContractByOffset(User user, Period period);
}

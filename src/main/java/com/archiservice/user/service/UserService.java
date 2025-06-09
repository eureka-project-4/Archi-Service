package com.archiservice.user.service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.common.security.CustomUser;
import com.archiservice.user.dto.request.PasswordUpdateRequestDto;
import com.archiservice.user.dto.response.ProfileResponseDto;
import com.archiservice.user.dto.response.TendencyResponseDto;

import java.util.List;

public interface UserService {
    ApiResponse<ProfileResponseDto> getUserProfile(CustomUser user);
    ApiResponse updatePassword(PasswordUpdateRequestDto request, CustomUser user);
    ApiResponse<List<TendencyResponseDto>> getUserTendency(CustomUser user);
}

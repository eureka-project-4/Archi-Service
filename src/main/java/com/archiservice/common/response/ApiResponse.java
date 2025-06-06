package com.archiservice.common.response;

import com.archiservice.common.enums.Result;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private Result result;
    private String message;
    private T data;
}

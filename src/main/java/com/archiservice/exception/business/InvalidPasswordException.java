package com.archiservice.exception.business;

import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }

    public InvalidPasswordException(String message) {
        super(ErrorCode.INVALID_PASSWORD, message);
    }
}

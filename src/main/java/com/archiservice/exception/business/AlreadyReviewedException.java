package com.archiservice.exception.business;

import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;

public class AlreadyReviewedException extends BusinessException {
    public AlreadyReviewedException() {
        super(ErrorCode.ALREADY_REVIEWED);
    }

    public AlreadyReviewedException(String message) {
        super(ErrorCode.ALREADY_REVIEWED, message);
    }
}

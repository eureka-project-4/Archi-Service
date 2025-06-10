package com.archiservice.exception.business;

import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;

public class NotMyReviewException extends BusinessException {
    public NotMyReviewException() {
        super(ErrorCode.NOT_MY_REVIEW);
    }

    public NotMyReviewException(String message) {
        super(ErrorCode.NOT_MY_REVIEW, message);
    }
}

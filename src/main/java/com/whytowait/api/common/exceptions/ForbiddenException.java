package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(ErrorType.FORBIDDEN, message, null);
    }

    public ForbiddenException() {
        super(ErrorType.FORBIDDEN, "Forbidden Error", null);
    }
}


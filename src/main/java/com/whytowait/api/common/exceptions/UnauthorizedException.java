package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(ErrorType.UNAUTHORIZED, message, null);
    }

    public UnauthorizedException() {
        super(ErrorType.UNAUTHORIZED, "Unauthorized Error", null);
    }
}

package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(ErrorType.BAD_REQUEST, message, null);
    }

    public BadRequestException() {
        super(ErrorType.BAD_REQUEST, "Bad Request Error", null);
    }
}


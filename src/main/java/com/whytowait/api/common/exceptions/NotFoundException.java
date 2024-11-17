package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(ErrorType.NOT_FOUND, message, null);
    }

    public NotFoundException() {
        super(ErrorType.NOT_FOUND, "Not Found Error", null);
    }
}

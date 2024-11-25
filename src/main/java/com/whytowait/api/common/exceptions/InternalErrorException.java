package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class InternalErrorException extends ApiException {
    public InternalErrorException(String message) {
        super(ErrorType.INTERNAL.INTERNAL, message, null);
    }

    public InternalErrorException() {
        super(ErrorType.INTERNAL, "Internal Error", null);
    }
}

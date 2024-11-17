package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class AccessTokenException extends ApiException {
    public AccessTokenException(String message) {
        super(ErrorType.ACCESS_TOKEN, message, null);
    }

    public AccessTokenException() {
        super(ErrorType.ACCESS_TOKEN, "Access Token Error", null);
    }
}

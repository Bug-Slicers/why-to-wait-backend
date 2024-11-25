package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class BadTokenException extends ApiException{
    public BadTokenException(String message){
        super(ErrorType.BAD_TOKEN, message, null);
    }

    public BadTokenException() {
        super(ErrorType.BAD_TOKEN, "Bad Token Error", null);
    }
}

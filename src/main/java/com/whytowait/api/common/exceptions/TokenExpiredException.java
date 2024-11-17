package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class TokenExpiredException extends ApiException{
    public TokenExpiredException(String message){
        super(ErrorType.TOKEN_EXPIRED, message, null);
    }

    public TokenExpiredException(){
        super(ErrorType.TOKEN_EXPIRED, "Token Expired Error", null);
    }
}

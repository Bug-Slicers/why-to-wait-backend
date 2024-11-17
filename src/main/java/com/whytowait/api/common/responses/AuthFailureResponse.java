package com.whytowait.api.common.responses;

import org.springframework.http.HttpStatus;

public class AuthFailureResponse<T> extends ApiResponse<T>{
    public AuthFailureResponse(String message){
        super(HttpStatus.UNAUTHORIZED, message, null, null, null);
    }

    public AuthFailureResponse() {
        super(HttpStatus.UNAUTHORIZED, "Authentication Failrue", null, null, null);
    }
}

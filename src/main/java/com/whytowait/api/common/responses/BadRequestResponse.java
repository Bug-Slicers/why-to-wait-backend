package com.whytowait.api.common.responses;

import org.springframework.http.HttpStatus;

public class BadRequestResponse<T> extends ApiResponse<T>{
    public BadRequestResponse(String message) {
        super(HttpStatus.BAD_REQUEST, message, null, null, null);
    }

    public BadRequestResponse() {
        super(HttpStatus.BAD_REQUEST, "Bad Request Error", null, null, null);
    }
}

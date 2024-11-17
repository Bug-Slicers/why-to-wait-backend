package com.whytowait.api.common.responses;

import org.springframework.http.HttpStatus;

public class InternalErrorResponse<T> extends ApiResponse<T> {
    public InternalErrorResponse(String message){
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, null, null, null);
    }

    public InternalErrorResponse() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", null, null, null);
    }
}

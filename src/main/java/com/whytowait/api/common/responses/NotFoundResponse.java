package com.whytowait.api.common.responses;

import org.springframework.http.HttpStatus;

public class NotFoundResponse<T> extends ApiResponse<T>{
    public NotFoundResponse(String message) {
        super(HttpStatus.NOT_FOUND, message, null, null, null);
    }

    public NotFoundResponse() {
        super(HttpStatus.NOT_FOUND, "Not Found Error", null, null, null);
    }
}

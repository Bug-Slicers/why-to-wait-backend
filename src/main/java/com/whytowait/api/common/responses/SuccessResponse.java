package com.whytowait.api.common.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class SuccessResponse<T> extends ApiResponse<T> {
    public SuccessResponse(String message, T data) {
        super(HttpStatus.OK, message, data, null, null);
    }

    public SuccessResponse(String message, T data, HttpHeaders customHeaders) {
        super(HttpStatus.OK, message, data, null, customHeaders);
    }

    public SuccessResponse(String message) {
        super(HttpStatus.OK, message, null, null, null);
    }
}

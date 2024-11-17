package com.whytowait.api.common.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class ValidationErrorResponse<T> extends ApiResponse<T> {
    public ValidationErrorResponse(T error) {
        super(HttpStatus.BAD_REQUEST, "Bad Request", null, error, null);
    }

    public ValidationErrorResponse(String message, T error) {
        super(HttpStatus.BAD_REQUEST, message, null, error, null);
    }

    public ValidationErrorResponse(T error, HttpHeaders customHeaders) {
        super(HttpStatus.BAD_REQUEST, "Bad Request", null, error, customHeaders);
    }

    public ValidationErrorResponse(String message, T error, HttpHeaders customHeaders) {
        super(HttpStatus.BAD_REQUEST, message, null, error, customHeaders);
    }
}

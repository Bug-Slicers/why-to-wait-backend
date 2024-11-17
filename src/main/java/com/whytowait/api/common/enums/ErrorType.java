package com.whytowait.api.common.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    BAD_TOKEN("BadTokenError"),
    TOKEN_EXPIRED("TokenExpiredError"),
    UNAUTHORIZED("AuthFailureError"),
    ACCESS_TOKEN("AccessTokenError"),
    INTERNAL("InternalError"),
    NOT_FOUND("NotFoundError"),
    NO_ENTRY("NoEntryError"),
    NO_DATA("NoDataError"),
    BAD_REQUEST("BadRequestError"),
    DATA_VALIDATION("DataValidationError"),
    FORBIDDEN("ForbiddenError");

    private final String errorCode;

    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }
}

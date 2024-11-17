package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class DataValidationException extends ApiException {
    public DataValidationException(String message, Object error) {
        super(ErrorType.DATA_VALIDATION, message, error);
    }

    public DataValidationException(Object error) {
        super(ErrorType.DATA_VALIDATION, "Data Validation Error", error);
    }
}

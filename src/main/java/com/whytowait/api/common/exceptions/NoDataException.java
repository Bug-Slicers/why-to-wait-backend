package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class NoDataException extends ApiException {
    public NoDataException(String message) {
        super(ErrorType.NO_DATA, message, null);
    }

    public NoDataException() {
        super(ErrorType.NO_DATA, "No Data Error", null);
    }
}

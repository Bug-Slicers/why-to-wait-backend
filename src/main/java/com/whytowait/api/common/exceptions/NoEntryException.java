package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;

public class NoEntryException extends ApiException {
    public NoEntryException(String message) {
        super(ErrorType.NO_ENTRY, message, null);
    }

    public NoEntryException() {
        super(ErrorType.NO_ENTRY, "No Entry Error", null);
    }
}


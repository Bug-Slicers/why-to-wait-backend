package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.enums.ErrorType;
import com.whytowait.api.common.responses.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiException extends Exception {
    ErrorType type;
    String message = "Error";
    Object error;

    public static ApiResponse handle(ApiException ex){
        return switch (ex.type) {
            case ErrorType.BAD_TOKEN, ErrorType.ACCESS_TOKEN, ErrorType.UNAUTHORIZED ->
                    new AuthFailureResponse(ex.message);
            case ErrorType.TOKEN_EXPIRED -> new TokenExpiredResponse(ex.message);
            case ErrorType.INTERNAL -> new InternalErrorResponse(ex.message);
            case ErrorType.NOT_FOUND, ErrorType.NO_ENTRY, ErrorType.NO_DATA -> new NotFoundResponse(ex.message);
            case ErrorType.BAD_REQUEST -> new BadRequestResponse(ex.message);
            case ErrorType.DATA_VALIDATION -> new ValidationErrorResponse(ex.message, ex.error);
            case ErrorType.FORBIDDEN -> new ForbiddenErrorResponse(ex.message);
            default -> new InternalErrorResponse();
        };
    }
}

package com.whytowait.api.common.exceptions;

import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.InternalErrorResponse;
import com.whytowait.api.common.responses.ValidationErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();

        result.getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );

        return new ValidationErrorResponse(errorMap);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleGenericException(Exception ex) {
        if(ex instanceof ApiException){
            return ApiException.handle((ApiException) ex);
        }
        return new InternalErrorResponse();
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleRunTimeException(RuntimeException ex){
        if(ex.getCause() instanceof ApiException){
            return  ApiException.handle((ApiException) ex.getCause());
        }
        return new InternalErrorResponse();
    };
}

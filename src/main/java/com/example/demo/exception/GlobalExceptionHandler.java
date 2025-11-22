package com.example.demo.exception;

import com.example.demo.dto.response.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ApiResponse handleException(Exception e) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ApiResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public ApiResponse handleValidationException(HandlerMethodValidationException e) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getReason());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ApiResponse handleNullPointerException(NullPointerException e) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = CustomException.class)
    public ApiResponse handleCustomException(CustomException e) {
        return ApiResponse.error(e.getHttpStatus(), e.getMessage());
    }
}
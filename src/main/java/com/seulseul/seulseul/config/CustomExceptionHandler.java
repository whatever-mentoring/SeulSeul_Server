package com.seulseul.seulseul.config;

import com.seulseul.seulseul.dto.Response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ResponseData errorResponse = new ResponseData(errorCode.getCode(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getCode()));
    }
}
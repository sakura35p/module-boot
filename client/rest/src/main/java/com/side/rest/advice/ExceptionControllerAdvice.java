package com.side.rest.advice;

import com.side.rest.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<String>> illegalStateException(IllegalStateException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.of(null, exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> illegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.of(null, exception.getMessage()));
    }
}

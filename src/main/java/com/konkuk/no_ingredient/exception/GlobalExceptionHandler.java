package com.konkuk.no_ingredient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice   // 모든 컨트롤러의 에러를 여기서 잡음
public class GlobalExceptionHandler {

    // 검색 결과가 없을 때(NoSuchElementException) 처리
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoResult(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}

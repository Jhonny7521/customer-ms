package com.bm_nttdata.customer_ms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "CUSTOMER_NOT_FOUND"
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateCustomerException(DuplicateCustomerException ex) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                "DUPLICATE_CUSTOMER"
        );
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private Map<String, Object> createErrorBody(int status, String message, String code) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("error", message);
        body.put("code", code);
        return body;
    }

}

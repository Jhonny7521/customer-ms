package com.bm_nttdata.customer_ms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación.
 * Centraliza el manejo de errores y proporciona respuestas consistentes
 * para las diferentes excepciones del sistema.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo CustomerNotFoundException.
     *
     * @param ex la excepción capturada cuando no se encuentra un cliente
     * @return ResponseEntity que contiene un Map con los detalles del error
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(
            CustomerNotFoundException ex) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "CUSTOMER_NOT_FOUND"
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja las excepciones de tipo DuplicateCustomerException.
     *
     * @param ex la excepción capturada cuando se intenta crear un cliente duplicado
     * @return ResponseEntity con el detalle del error en formato Map
     */
    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateCustomerException(
            DuplicateCustomerException ex) {
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

package com.cooperativismo.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro de negócio", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegal(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "CPF inválido", ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse response = new ErrorResponse(status.value(), error, message);
        return new ResponseEntity<>(response, status);
    }
}

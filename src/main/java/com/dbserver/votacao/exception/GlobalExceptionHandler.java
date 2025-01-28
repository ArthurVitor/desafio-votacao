package com.dbserver.votacao.exception;

import com.dbserver.votacao.dto.Error.ErrorResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException ex) {
        return this.buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponseDto> handlePropertyReference(PropertyReferenceException ex) {
        return this.buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
        return this.buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return this.buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message, status.value());

        return ResponseEntity.status(status).body(errorResponseDto);
    }
}

package com.example.sri.ArtemRomanenkoS32237.config;

import com.example.sri.ArtemRomanenkoS32237.config.exceptions.ResourceNotFoundException;
import com.example.sri.ArtemRomanenkoS32237.dto.ErrorMessageDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDto> handleAllExceptions(Exception ex) {
        ErrorMessageDto errorMessage = ErrorMessageDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage("Serwer error : " + ex.getMessage())
                .occurredOn(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessageDto errorMessage = ErrorMessageDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage("Resource not found: " + ex.getMessage())
                .occurredOn(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageDto> handleIllegalArgumentException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));
        ErrorMessageDto errorMessage = ErrorMessageDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .occurredOn(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDto> handleTransactionSystemException(TransactionSystemException ex) {
        ErrorMessageDto errorMessage = ErrorMessageDto.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage("Resource not found: " + ex.getCause().getCause().getMessage())
                .occurredOn(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
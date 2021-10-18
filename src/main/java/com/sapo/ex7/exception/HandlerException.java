package com.sapo.ex7.exception;


import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.dto.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    ErrorMessage errorMessage = ErrorMessage.builder()
        .message("An error has occurred")
        .errors(errors)
        .timestamp(new Date())
        .status(404)
        .build();
    AbstractResponse result = AbstractResponse.builder()
        .meta(errorMessage)
        .build();
    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> HandlerArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));
    ErrorMessage errorMessage = ErrorMessage.builder()
        .message("Validation failed for argument")
        .timestamp(new Date())
        .errors(errors)
        .status(409)
        .build();
    AbstractResponse result = AbstractResponse.builder()
        .meta(errorMessage)
        .build();
    return new ResponseEntity<>(result, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {EntityAlreadyExistsException.class})
  public ResponseEntity<Object> entityAlreadyExistsException(EntityAlreadyExistsException ex) {
    ErrorMessage errorMessage = ErrorMessage.builder()
        .message(ex.getMessage())
        .timestamp(new Date())
        .status(409)
        .build();
    AbstractResponse result = AbstractResponse.builder()
        .meta(errorMessage)
        .build();
    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {SQLException.class})
  public ResponseEntity<Object> sqlException(SQLException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    errors.put("sqlState", ex.getSQLState());
    ErrorMessage errorMessage = ErrorMessage.builder()
        .message("An error has occurred")
        .errors(errors)
        .timestamp(new Date())
        .status(409)
        .build();
    AbstractResponse result = AbstractResponse.builder()
        .meta(errorMessage)
        .build();
    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
  }
}

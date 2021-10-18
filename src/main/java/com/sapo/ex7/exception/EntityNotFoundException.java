package com.sapo.ex7.exception;


public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException() {
  }

  public EntityNotFoundException(String message) {
    super(message);
  }
}

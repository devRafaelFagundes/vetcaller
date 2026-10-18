package com.vetcaller.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyInUseException extends ApiException {

  public ResourceAlreadyInUseException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}

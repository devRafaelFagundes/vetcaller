package com.vetcaller.exceptions;

import org.springframework.http.HttpStatus;

public class ConflitException extends ApiException {
    public ConflitException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

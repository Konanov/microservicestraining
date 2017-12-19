package com.awesomeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSubject extends RuntimeException {
    public InvalidSubject(String message) {
        super(message);
    }
}

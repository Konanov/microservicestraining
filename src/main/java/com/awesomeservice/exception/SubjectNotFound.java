package com.awesomeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectNotFound extends RuntimeException {
    public SubjectNotFound(String message) {
        super(message);
    }
}

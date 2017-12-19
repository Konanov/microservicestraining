package com.awesomeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ExceptionalResponse {
    private Date timestamp;
    private String message;
    private String details;
}

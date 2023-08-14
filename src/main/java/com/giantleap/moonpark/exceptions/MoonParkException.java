package com.giantleap.moonpark.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MoonParkException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    public MoonParkException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}

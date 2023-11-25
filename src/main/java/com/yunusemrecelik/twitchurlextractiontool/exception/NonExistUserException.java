package com.yunusemrecelik.twitchurlextractiontool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NonExistUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NonExistUserException(String message) {
        super(message);
    }
}

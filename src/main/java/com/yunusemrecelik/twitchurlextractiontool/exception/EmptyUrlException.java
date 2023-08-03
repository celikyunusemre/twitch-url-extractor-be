package com.yunusemrecelik.twitchurlextractiontool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyUrlException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyUrlException(String message) {
        super(message);
    }
}

package com.yunusemrecelik.twitchurlextractiontool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyUrlException.class)
    public final ResponseEntity<ErrorResponse> handleUrlNotFoundException(
            EmptyUrlException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getLocalizedMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EmptyChannelNameException.class)
    public final ResponseEntity<ErrorResponse> handleChannelNameIsEmptyException(
            EmptyChannelNameException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getLocalizedMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}

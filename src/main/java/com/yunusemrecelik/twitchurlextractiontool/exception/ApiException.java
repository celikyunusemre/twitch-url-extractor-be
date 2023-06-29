package com.yunusemrecelik.twitchurlextractiontool.exception;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class ApiException {
    private final String message;
    private final ZonedDateTime timeStamp;
}

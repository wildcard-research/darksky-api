package com.github.wildcardresearch.darkskyapi.exception;

public class ForcastException extends RuntimeException {
    public ForcastException(String message) {
        super(message);
    }
    public ForcastException(String message, Throwable ex) {
        super(message, ex);
    }
}
package com.education.services.exceptions;

public class BaseApplicationException extends RuntimeException{
    public BaseApplicationException() {
    }

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

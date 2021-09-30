package com.education.model;

import org.springframework.http.HttpStatus;

public class ResponseException {
    private String exceptionMessage;
    private HttpStatus httpStatus;

    public ResponseException(String exceptionMessage, HttpStatus httpStatus) {
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

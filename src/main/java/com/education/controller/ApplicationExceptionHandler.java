package com.education.controller;

import com.education.model.ResponseException;
import com.education.services.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handle(UserNotFoundException e, WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handle(TaskNotFoundException e, WebRequest request){
        logger.info("Unable to process request {}:{}",request,e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDeletedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseException handle(UserDeletedException e,WebRequest request){
        logger.info("Unable to process request {}:{}",request,e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.GONE);
    }

    @ExceptionHandler(TaskIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseException handle(TaskIllegalArgumentException e,WebRequest request){
        logger.info("Unable to process request {}:{}",request,e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UniqueEmailException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseException handle(UniqueEmailException e,WebRequest request){
        logger.info("Unable to process request {}:{}",request,e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handle(BaseApplicationException e){
        logger.warn("Unable to catch exception: "+e.getMessage(),e);
        return new ResponseException(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handle(Exception e) {
        logger.error("An error handled "+e.getMessage(),e);
        return new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

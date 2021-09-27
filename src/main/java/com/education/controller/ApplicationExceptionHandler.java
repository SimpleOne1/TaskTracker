package com.education.controller;

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
    public String handle(UserNotFoundException e, WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(TaskNotFoundException e, WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(UserDeletedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String handle(UserDeletedException e,WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(TaskIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handle(TaskIllegalArgumentException e,WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(UniqueEmailException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handle(UniqueEmailException e,WebRequest request){
        logger.error("Unable to process request {}:{}",request,e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(BaseApplicationException e){
        logger.warn("Unable to catch exception: "+e.getMessage(),e);
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Exception e){
        logger.error("An error handled"+e.getMessage(),e);
        return e.getMessage();
    }
}

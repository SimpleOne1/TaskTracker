package com.education.services.exceptions;

public class TaskNotFoundException extends BaseApplicationException{
    public TaskNotFoundException(Long id) {
        super("Task by id "+id+" was not found");
    }
}

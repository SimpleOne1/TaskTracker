package com.education.services.exceptions;

public class TaskIllegalArgumentException extends BaseApplicationException{
    public TaskIllegalArgumentException() {
        super("Mandatory fields in task  are null");
    }
}

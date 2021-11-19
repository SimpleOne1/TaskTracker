package com.education.services.exceptions;

public class ProjectNotFoundException extends BaseApplicationException{
    public ProjectNotFoundException(Long id) {
        super("Project by id: "+id+" was not found.");
    }
}

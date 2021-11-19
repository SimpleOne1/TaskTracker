package com.education.services.exceptions;

public class TeamNotFoundException extends BaseApplicationException{
    public TeamNotFoundException(Long id) {
        super("Team by id: "+id+" was not found.");
    }
}

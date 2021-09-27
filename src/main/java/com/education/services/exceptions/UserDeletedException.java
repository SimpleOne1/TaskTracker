package com.education.services.exceptions;

public class UserDeletedException extends BaseApplicationException{
    public UserDeletedException(Long id) {
        super("User by id "+ id +" was deleted");
    }
}

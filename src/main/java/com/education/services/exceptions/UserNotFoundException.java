package com.education.services.exceptions;

public class UserNotFoundException extends BaseApplicationException{

    public UserNotFoundException(Long userId) {
        super("User by id "+userId+" was not found");
    }

    public UserNotFoundException(String email) {
        super("User by unique email "+email+" was not found");
    }
}

package com.education.services.exceptions;

public class UniqueEmailException extends BaseApplicationException{
    public UniqueEmailException() {
        super("Email uniqueness was violated");
    }

    public UniqueEmailException(String message) {
        super("The following action caused email uniqueness violation : "+message);
    }
}

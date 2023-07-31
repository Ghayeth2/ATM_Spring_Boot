package com.atm.core.exception;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message) {
        super(message);
    }
}

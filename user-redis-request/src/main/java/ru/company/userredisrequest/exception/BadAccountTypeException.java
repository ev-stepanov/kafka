package ru.company.userredisrequest.exception;

public class BadAccountTypeException extends RuntimeException{
    public BadAccountTypeException(String message) {
        super(message);
    }
}

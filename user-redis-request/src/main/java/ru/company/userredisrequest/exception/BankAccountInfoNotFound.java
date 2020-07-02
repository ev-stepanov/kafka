package ru.company.userredisrequest.exception;

public class BankAccountInfoNotFound extends RuntimeException {
    public BankAccountInfoNotFound(String message) {
        super(message);
    }
}

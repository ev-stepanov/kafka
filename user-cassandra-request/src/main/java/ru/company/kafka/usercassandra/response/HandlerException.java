package ru.company.kafka.usercassandra.response;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseView IllegalArgumentException(RuntimeException e) {
        return new ErrorResponseView("Wrong uuid");
    }
}

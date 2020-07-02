package ru.company.userredisrequest.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* ru.company.userredisrequest.service.*.*(..))")
    public void logServices(JoinPoint joinPoint) {
        log.info("Method invoke: {}", joinPoint.getSignature().getName());
        log.info("Method arguments before invoke: {}", Arrays.toString(joinPoint.getArgs()));
    }
}

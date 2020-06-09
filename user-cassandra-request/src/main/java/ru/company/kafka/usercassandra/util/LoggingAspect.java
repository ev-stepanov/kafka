package ru.company.kafka.usercassandra.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* ru.company.kafka.usercassandra.service.*.*(..))")
    public Object logServices(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Method invoke: " + joinPoint.getSignature().getName());
        log.info("Method arguments before invoke: " + Arrays.toString(joinPoint.getArgs()));
        Object proceed = joinPoint.proceed();
        log.info("Method return values: " + proceed);
        return proceed;
    }
}

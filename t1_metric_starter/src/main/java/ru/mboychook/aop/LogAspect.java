package ru.mboychook.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
@Order(0)
public class LogAspect {

    @Before("@annotation(ru.mboychook.aop.LogExecution)")
    @Order(1)
    public void logAnnotationBefore(JoinPoint joinPoint) {
        log.info("ASPECT BEFORE ANNOTATION: Call method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(ru.mboychook.aop.LogException)")
    @Order(0)
    public void logExceptionAnnotation(JoinPoint joinPoint) {
        System.err.println("ASPECT EXCEPTION ANNOTATION: Logging exception: {}" + joinPoint.getSignature().getName());
    }
}

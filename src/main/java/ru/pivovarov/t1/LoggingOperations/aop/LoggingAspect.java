package ru.pivovarov.t1.LoggingOperations.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut(value = "execution(* ru.pivovarov.t1.LoggingOperations.service.*.*(..))")
    public void callServices() {}

    @Pointcut(value = "within(ru.pivovarov.t1.LoggingOperations.controller.UserRestController)")
    public void callControllers() {}

    @AfterThrowing(pointcut = "callServices() || callControllers()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("log exception {}() - {}", methodName, exception.getMessage());
    }

    @Before(value = "callControllers()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info("log before {}() - {}", methodName, Arrays.toString(args));
        log.info("Method args names: {}", String.join("\n", methodSignature.getParameterNames()));
    }
}

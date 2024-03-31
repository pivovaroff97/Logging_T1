package ru.pivovarov.t1.LoggingOperations.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class OrderServiceAspect {

    @Pointcut(value = "execution(* ru.pivovarov.t1.LoggingOperations.service.OrderServiceImpl.*(..))")
    public void callOrderService() {}
    @AfterReturning(value = "callOrderService()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("log after returning {}() - {}", methodName, result);
    }
    @Around("bean(orderServiceImpl)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        log.info("Выполнение метода {} с аргументами {}", methodName, args);
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Метод {} выполнился за {}, с результатом {}", methodName, endTime - startTime, result);
        return result;
    }
}

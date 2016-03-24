package com.oneandone.bank.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    //aplic metodelor publice care sunt din pachetul de mai jos, with all functions and params
    @Around("execution(public * com.oneandone.bank.*.*Service+.*(..))")
    public Object log(ProceedingJoinPoint serviceMethod) throws Throwable {
        String name = createJoinPointTraceName(serviceMethod);
        System.out.println("-Start " + name + " with params " + Arrays.toString(serviceMethod.getArgs()));
        try {
            Object returnedValue = serviceMethod.proceed();
            System.out.println("-  End " + name + " returned " + returnedValue);
            return returnedValue;
        } catch (Exception e) {
            System.out.println("Got exception for " + name + " exception: " + e.getMessage());
            throw e;
        }
    }

    private String createJoinPointTraceName(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringType().getSimpleName() + '.' + signature.getName();
    }


}

package com.oneandone.bank.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/* profile - how long is the callling methods */

@Aspect
@Component
public class ProfileAspect {
    //TODO AOP - configure the pointcut to match all services methods
    // pointcut syntax:   execution(<return type> <package>.<Class>.<method>(params))
    @Around("execution(public * com.oneandone.bank.*.*Service+.*(..))")
    public Object profile(ProceedingJoinPoint serviceMethod) throws Throwable {

        String name = createJoinPointTraceName(serviceMethod);
        //TODO AOP - implement the profile behavior

        long startTime = System.currentTimeMillis();

        try {
           return serviceMethod.proceed();

        }
        finally {
            System.out.println("-Start " + name + " time took " + (System.currentTimeMillis() - startTime) + "ms");
        }


    }

    private String createJoinPointTraceName(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringType().getSimpleName() + '.' + signature.getName();
    }

}

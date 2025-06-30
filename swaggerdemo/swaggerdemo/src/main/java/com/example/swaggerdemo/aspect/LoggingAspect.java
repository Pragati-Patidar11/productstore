package com.example.swaggerdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.swaggerdemo.service.ProductService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println(" Entering method: " + joinPoint.getSignature());
    }

    @After("execution(* com.example.swaggerdemo.service.ProductService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println(" Exiting method: " + joinPoint.getSignature());
    }
}

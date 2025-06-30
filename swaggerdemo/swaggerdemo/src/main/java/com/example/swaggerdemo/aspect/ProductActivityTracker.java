package com.example.swaggerdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductActivityTracker {


    @AfterReturning(value = "execution(* com.example.swaggerdemo.service.ProductService.saveProduct(..)) || " +
            "execution(* com.example.swaggerdemo.service.ProductService.updateProduct(..))",
            returning = "product")
    public void auditProductChanges(JoinPoint joinPoint, Object product) {
        System.out.println(" AUDIT: Product saved or updated... " + product);
    }
}


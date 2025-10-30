package com.example.spring_restapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VerifyLikeAspect {

    @Pointcut("execution(*com.example.service.LikeService.*like(..))")
    public void likeServiceMethods() {}

    @Around("likeServiceMethods()")
    public void validateLike(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Object[] args = joinPoint.getArgs();

        System.out.println(args[0]);
        System.out.println(args[1]);
    }
}

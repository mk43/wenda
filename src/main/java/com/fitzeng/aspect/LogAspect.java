package com.fitzeng.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


@Aspect
@Component
public class LogAspect {
    //    private static final Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.fitzeng.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            stringBuilder.append("arg : " + arg.toString() + "|");
        }
        logger.info("before method: " + stringBuilder.toString());
    }

    @After("execution(* com.fitzeng.controller.IndexController.*(..))")
    public void afterMethod() {
        logger.info("after method " + new Date().toString());
    }
}

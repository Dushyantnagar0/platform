package com.org.platform.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.org.platform.utils.Utility.toJsonString;


@Slf4j
@Aspect
@Component
// not sure why but after commenting this, annotation is working
//@ConditionalOnExpression("${aspect.enabled:true}")
public class PrintJsonAdvice {

    @Around("@annotation(com.org.platform.annotations.PrintJson)")
    public Object printJson(ProceedingJoinPoint point) throws Throwable {
        Object object = point.proceed();
        log.info("Class Name: "+ point.getSignature().getDeclaringTypeName() +". Method Name: "+ point.getSignature().getName() + "object in json : {}", toJsonString(object));
        return object;
    }

}

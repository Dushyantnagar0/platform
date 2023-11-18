package com.org.platform.annotations;

//import com.amazonaws.util.json.Jackson;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Slf4j
//@ConditionalOnExpression("${aspect.enabled:true}")
//public class PrintJsonAdvice {
//
//    @Around("@annotation(com.org.platform.annotations.PrintJson)")
//    public Object printJson(ProceedingJoinPoint point) throws Throwable {
//        Object object = point.proceed();
//        log.info("Class Name: "+ point.getSignature().getDeclaringTypeName() +". Method Name: "+ point.getSignature().getName() + "object in json : {}", Jackson.toJsonString(object));
//        return object;
//    }
//
//}

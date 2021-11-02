package ru.lonelydutchhound.remotedevicecontrol.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.UUID;

@Order(1)
@Service
@Aspect
public class MDCInitializerAspect {
  private static final String TRACE_ID = "traceId";

  @Around("methodsAnnotatedWithMethodWithMdcContext()")
  public Object aroundAnnotatedMethods (ProceedingJoinPoint joinPoint) throws Throwable {
    setMdcContextForMethod(joinPoint);
    return joinPoint.proceed();
  }

  @Pointcut(value = "@annotation(MethodWithMDC)")
  public void methodsAnnotatedWithMethodWithMdcContext () {
  }

  private void setMdcContextForMethod (ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    MethodWithMDC annotation = method.getAnnotation(MethodWithMDC.class);

    MDC.put(TRACE_ID, UUID.randomUUID().toString());
  }
}

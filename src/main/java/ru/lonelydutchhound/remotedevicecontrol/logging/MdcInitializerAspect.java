package ru.lonelydutchhound.remotedevicecontrol.logging;

import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service
@Aspect
public class MdcInitializerAspect {
  private static final String TRACE_ID = "traceId";

  @Around("methodsAnnotatedWithMethodWithMdc()")
  public Object aroundAnnotatedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
    setMdcContextForMethod();
    return joinPoint.proceed();
  }

  @Pointcut(value = "@annotation(MethodWithMdc)")
  public void methodsAnnotatedWithMethodWithMdc() {
  }

  private void setMdcContextForMethod() {
    MDC.put(TRACE_ID, UUID.randomUUID().toString());
  }
}

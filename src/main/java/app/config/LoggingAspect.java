package app.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  @Before("execution(* app.api.controller..*(..))")
  public void logMethodName(JoinPoint joinPoint) {
    System.out.println("Calling method: " + joinPoint.getSignature().getName());
  }
}

package app.config;

import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@Getter
public class LoggingAspect {

  public int count = 0;

  @Before("execution(* app.api.controller..*(..))")
  public void logMethodName(JoinPoint joinPoint) {
    count++;
    System.out.println("Calling method: " + joinPoint.getSignature().getName());
  }

  @Around("execution(* app.api.controller..*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    count++;
    Instant start = Instant.now();
    Object result = joinPoint.proceed();
    Instant end = Instant.now();
    System.out.println("Execution time of " + joinPoint.getSignature().getName() + ": " +
        Duration.between(start, end).toMillis() + " ms");
    return result;
  }
}

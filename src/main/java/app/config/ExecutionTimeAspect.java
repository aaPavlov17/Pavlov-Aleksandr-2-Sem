package app.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.Duration;

@Aspect
@Component
public class ExecutionTimeAspect {

  @Around("execution(* app.api.controller..*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant start = Instant.now();
    Object result = joinPoint.proceed();
    Instant end = Instant.now();
    System.out.println("Execution time of " + joinPoint.getSignature().getName() + ": " +
        Duration.between(start, end).toMillis() + " ms");
    return result;
  }
}

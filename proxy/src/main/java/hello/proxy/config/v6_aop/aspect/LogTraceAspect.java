package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        //joinPoint에 정보들이 다 들어가있음, 꺼내서 확인해볼 수도 있음
        log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
        log.info("getArgs={}", joinPoint.getArgs()); //전달인자
        log.info("getSignature={}", joinPoint.getSignature()); //join point 시그니처

        try {
//            Method method = invocation.getMethod();
//            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //로직 호출
//            Object result = invocation.proceed();
            Object result = joinPoint.proceed(); //예외 처리 필요

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}

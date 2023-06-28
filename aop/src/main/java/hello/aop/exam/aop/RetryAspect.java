package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)") //proceed() 호출해야하므로 @Before 말고 @Around 사용해야함
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable { //retry 파라미터 받기 위해서 이름 일치시킴
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); //@annotation(retry)과 파라미터 Retry retry로 간편하게 값을 가져올 수 있다.
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}

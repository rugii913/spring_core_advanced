package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //hello.aop.order 패키지와 하위 패키지 포인트컷 분리 - 재사용 가능한 장점
    @Pointcut("execution(* hello.aop.order..*(..))") //pointcut expression
    private void allOrder(){} //pointcut signature
    
    @Around("allOrder()") // pointcut signature allOrder()를 가리킴
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처 - 어떤 메서드가 호출됐는지 정보 찍힘
        return joinPoint.proceed(); //타겟 호출
    }
}

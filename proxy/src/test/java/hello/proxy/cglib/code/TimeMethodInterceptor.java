package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target; //항상 프록시 로직에는 호출할 대상 클래스 필요

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        //Object result = method.invoke(target, args);
        Object result = methodProxy.invoke(target, args); //CGLIB에서는 최적화 관련하여 MethodProxy를 사용하는 게 더 빠르다고 함(매뉴얼 내용)

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}

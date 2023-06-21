package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        //공통 로직2 종료
        //===========> 위 두 부분 호출하는 메서드만 다르고 로직은 똑같다
        //여기서 공통 로직1과 공통 로직2를 하나의 메서드로 뽑아서 합칠 수 있을까???
        //메서드로 뽑아서 공통화 하는 건 생각보다 어렵다. - 중간에 호출하는 메서드 자체가 다르기 때문
    }

    //cf. 람다를 사용해서 공통화하는 것도 가능하지만 목적이 리플렉션 학습이므로 생략

    @Test
    void reflection1() throws Exception {
        //클래스 메타정보 획득
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        //Hello 인스턴스(target이 될 것) 만듦
        Hello target = new Hello();

        //callA 메서드 메타정보 획득 - 메서드 이름 문자열을 통해서 -> 동적으로 바뀔 수 있는 부분
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서드 메타정보 획득 - 메서드 이름 문자열을 통해서 -> 동적으로 바뀔 수 있는 부분
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }
    //===> 기존 callA(), callB() 메서드를 직접 호출하는 부분이 Method로 대체(추상화)
    //cf. Method는 java.lang.reflect 패키지의 클래스

    @Test
    void reflection2() throws Exception {
        //클래스 메타정보 획득
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        //Hello 인스턴스(target이 될 것) 만듦
        Hello target = new Hello();

        //callA 메서드 메타정보 획득 -> dynamicCall(~)로 넘김
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //callB 메서드 메타정보 획득 -> dynamicCall(~)로 넘김
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        /*
        // reflection0()에서 target.callA() 부분 공통화가 안 되던 것을 해결
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        */
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}

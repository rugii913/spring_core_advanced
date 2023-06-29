package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ObjectProvider(Provider), ApplicationContext를 사용해서 지연(LAZY) 조회
 */
@Slf4j
@Component
public class CallServiceV2 {

    /*
    private final ApplicationContext applicationContext; //가장 날 것인 방법 ApplicationContext에서 꺼내오기
    // ApplicationContext는 스프링이 주입받을 수 있도록 기본적으로 제공함
    public CallServiceV2(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    //문제: ApplicationContext는 기능이 너무 많은 거대한 클래스임.
    //그런데 우리가 원하는 것은 딱 한 가지 기능 - 빈을 지연해서 조회하기
    */

    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //외부 메서드 호출하는 모양새가 됨
    }

    public void internal() {
        log.info("call internal");
    }
}

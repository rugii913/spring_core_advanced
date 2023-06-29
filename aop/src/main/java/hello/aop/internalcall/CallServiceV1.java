package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 참고: 생성자 주입은 순환 사이클을 만들기 때문에 실패한다.
 * 참고: 스프링 부트 2.6부터 순환 참조 금지 정책으로 인해 setter 주입도 어플리케이션 로딩에 실패한다.
 * application.properties에 spring.main.allow-circular-references=true 추가해야 순환 참조 가능
 */
@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

//    @Autowired
//    public void setCallServiceV1(CallServiceV1 callServiceV1) {
//        this.callServiceV1 = callServiceV1;
//    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); //외부 메서드 호출하는 모양새가 됨
    }

    public void internal() {
        log.info("call internal");
    }
}

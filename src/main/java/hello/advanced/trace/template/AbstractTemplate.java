package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();

    //템플릿 메서드 패턴 부연 설명
    //좋은 설계란 변경이 발생할 때 드러남 - 단순히 소스코드 몇 줄을 줄인 게 전부가 아니라
    //템플릿 메서드 패턴을 적용해서 단일 책임 원칙(SRP)를 지키게 됨
    //-> 변경 지점을 하나로 모아서 변경에 쉽게 대처할 수 있는 구조

    //템플릿 메서드 패턴 - GoF 디자인 패턴에서 정의
    //부모 클래스에 알고리즘의 골격인 템플릿을 정의
    //일부 변경되는 로직은 자식 클래스에 정의
    //-> 자식클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의 가능
    //상속과 오버라이딩을 통한 다형성으로 문제 해결

    //하지만 상속을 사용해서 발생하는 문제들 -> 자식-부모 컴파일 시점에 강하게 결합
    //---> call() 이거 하나 재정의한 건데 부모의 거대한 로직을 다 알고 있어야함
    //자식클래스 extends 부모클래스 -> 강하게 의존: 자식 클래스의 코드에 부모 클래스의 코드가 명확하게 적혀 있다.

    //UML 삼각형 화살표 "->" 의 의미 (1) 상속한다. (2) 의존한다.
    //부모 클래스를 수정하면, 자식 클래스에도 영향 => 좋은 설계가 아니다.

    //====> 템플릿 메서드 패턴과 비슷한 역할을 하면서 상속의 단점을 제거할 수 있는 디자인 패턴: 전략 패턴
    //상속보다는 위임, composition, 구성
}

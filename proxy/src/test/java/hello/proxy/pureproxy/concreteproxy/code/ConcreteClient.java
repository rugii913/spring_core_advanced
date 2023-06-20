package hello.proxy.pureproxy.concreteproxy.code;

public class ConcreteClient {

    private ConcreteLogic concreteLogic; //ConcreteLogic, TimeProxy 모두 주입 가능
    // - 클라이언트는 ConcreteLogic이 들어왔는지, TimeProxy가 들어왔는지 모른다.

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute() {
        concreteLogic.operation();
    }
}

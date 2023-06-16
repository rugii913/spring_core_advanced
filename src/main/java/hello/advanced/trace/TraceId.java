package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id; //트랜잭션 ID
    private int level; //메서드 호출 깊이

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) { //private으로 함
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
        //UUID에서 앞 8자리만 사용 - 거의 중복 안 되고, 어쩌다 중복되더라도 큰 문제 안 됨
    }

    public TraceId createNextId() { //public으로 함 - 외부에서 쓸 것
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() { //public으로 함 - 외부에서 쓸 것
        return new TraceId(id, level + 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}

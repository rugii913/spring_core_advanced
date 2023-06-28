package hello.aop.exam;

import hello.aop.exam.annotation.Retry;
import hello.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 요청
     */
    @Trace
    @Retry(value = 4) //default에서 value 바꿔줄 수 있음 //어차피 값 하나밖에 없으므로 @Retry(4)로 생략해서 적어도 됨
    // !!주의 retry 횟수에 제한이 없으면, 셀프 디도스...가 되므로 유의할 것
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}

package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        log.info("비지니스 로직 1 실행"); // 비지니스 로직

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        log.info("비지니스 로직 2 실행"); // 비지니스 로직

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    @DisplayName("템플릿 메서드 패턴 적용")
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        AbstractTemplate template2 = new SubClassLogic1();

        template1.execute();
        template2.execute();
    }

    @DisplayName("템플릿 메서드 패턴, 악명 내부 클래스 적용")
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직 1 호출");
            }
        }; // 익명 내부 클래스, SubClassLogicX 클래스를 계속해서 만들 필요가 없다. 그때그때 사용할 때, 만든다.
        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직 2 호출");
            }
        };

        log.info("클래스 이름 1 = {}", template1.getClass());
        log.info("클래스 이름 2 = {}", template2.getClass());

        template1.execute();
        template2.execute();
    }
}

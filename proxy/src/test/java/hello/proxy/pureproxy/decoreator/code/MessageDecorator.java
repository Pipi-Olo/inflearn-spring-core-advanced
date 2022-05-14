package hello.proxy.pureproxy.decoreator.code;

import lombok.extern.slf4j.Slf4j;
import org.mockito.ScopedMock;

@Slf4j
public class MessageDecorator implements Component {

    private final RealComponent target;

    public MessageDecorator(RealComponent target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String operation = target.operation();
        String deco = "*****" + operation + "*****";

        log.info("MessageDecorator 꾸미기 적용 전 = {}, 적용 후 = {}", operation, deco);
        return deco;
    }
}

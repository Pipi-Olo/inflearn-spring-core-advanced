package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller 는 인터페이스 잘 사용 안 함.
 */
@RequestMapping // 스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId); // 인터페이스에서는 "itemId" 명시하는 것이 좋다. 버전에 따라 인식 못 하는 경우가 있기 때문이다.

    @GetMapping("/v1/no-log")
    String noLog();
}

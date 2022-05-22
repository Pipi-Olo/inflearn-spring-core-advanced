package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    // hello.aop.order 하위 패키지 && 클래스 이름 패턴 *Service
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature()); // @Before
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature()); // @AfterReturning
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature()); // @AfterThrowing
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature()); // @After
        }
    }

    /**
     * joinPoint 실행 직전까지 필요한 로직을 적는다.
     * joinPoint.proceed() 는 스프링이 자동으로 실행시켜준다.
     */
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     * joinPoint 의 return 값이 result 에 들어온다.
     * target 의 return 값을 받을 수 있는 타입으로 지정해야 한다.
     * 예를 들어서, OrderService 는 void 반환이기 때문에, String result 로 받을 수 없다.
     *           OrderRepository 는 String 반환이기 때문에, String result 가능하다.
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} result={}", joinPoint.getSignature(), result);
    }

    /**
     * Object result 와 마찬가지로 Exception ex 도 자기 하위 타입의 예외는 받을 수 있으나, 상위 타입의 예외는 받을 수 없다.
     * -> 자바 상속 기본 문법
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex);
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}

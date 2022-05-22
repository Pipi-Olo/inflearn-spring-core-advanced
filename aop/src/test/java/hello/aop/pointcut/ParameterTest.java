package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.assertj.core.condition.Join;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        /**
         * @param arg : String 으로 선언되었기 때문에 Pointcut 자체가 String 만 받는다.
         * @Before : 반환값 X, target 실행 필요없다. -> joinPoint 필요없다. -> 예외 필요없다.
         */
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        /**
         * @param obj : MemberService 로 선언되었기 때문에 Pointcut 이 MemberService 만 받는다.
         *              MemberService 의 프록시 객체가 들어온다.
         */
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        /**
         * @param obj : 실제 target 객체가 들어온다. / 나머지는 this 와 동일하다.
         *              this : 프록시 객체가 필요할 때,
         *              target : 실제 target 객체가 필요할 때
         */
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTatget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target] {}, annotation={}", joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within] {}, annotation={}", joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@within] {}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
    }
}

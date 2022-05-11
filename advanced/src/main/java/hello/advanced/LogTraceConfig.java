package hello.advanced;

import hello.advanced.trace.Logtrace.FieldLogTrace;
import hello.advanced.trace.Logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}

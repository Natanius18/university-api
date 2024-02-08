package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@Aspect
@RequiredArgsConstructor
public class LogService {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;


    @Before(value = "within(software.sigma.internship.controller.*))")
    private void sendInfoToLogService(JoinPoint joinPoint) {
        var params = stream(joinPoint.getArgs())
            .map(arg -> ofNullable(arg).map(Object::toString).orElse("empty"))
            .toList();
        Map<String, Object> map = new HashMap<>();
        map.put("principal", getPrincipal());
        map.put("method", joinPoint.getSignature().toString());
        map.put("params", params);
        map.put("date", new Date());
        kafkaTemplate.send("logs", map);
    }

    private String getPrincipal() {
        var authentication = getContext().getAuthentication();
        var enabled = stream(authentication.getPrincipal().toString().split(", "))
            .filter(string -> string.startsWith("Enabled"))
            .findFirst()
            .map(s -> ", " + s)
            .orElse("");
        return authentication.getName() + ", Authorities: " + authentication.getAuthorities() + enabled;
    }
}

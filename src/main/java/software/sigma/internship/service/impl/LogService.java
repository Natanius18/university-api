package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Aspect
@RequiredArgsConstructor
public class LogService {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;


    @Before(value = "within(software.sigma.internship.controller.*))")
    private void sendInfoToLogService(JoinPoint joinPoint) {
        List<String> params = Arrays
                .stream(joinPoint.getArgs())
                .map(arg -> (arg != null) ? arg.toString() : "empty")
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("principal", getPrincipal());
        map.put("method", joinPoint.getSignature().toString());
        map.put("params", params);
        map.put("date", new Date());
        System.out.println(map);
        kafkaTemplate.send("logs", map);
    }

    private String getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> enabledOptional = Arrays
                .stream(authentication.getPrincipal().toString().split(", "))
                .filter(string -> string.startsWith("Enabled"))
                .findFirst();
        String enabled = enabledOptional.map(s -> ", " + s).orElse("");
        return authentication.getName() + ", Authorities: " + authentication.getAuthorities() + enabled;
    }
}

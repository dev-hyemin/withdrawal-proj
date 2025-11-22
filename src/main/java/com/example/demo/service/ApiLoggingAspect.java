package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class ApiLoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logApi(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Request
        Object[] args = pjp.getArgs();
        String requestBody = objectMapper.writeValueAsString(args);
        log.info("""
                \n[API REQUEST]
                URI: {}
                Method: {}
                Body: {}
                """,
                uri, method, requestBody);

        // Response
        try {
            Object response = pjp.proceed();
            log.info("""
                    \n[API RESPONSE]
                    Response: {}
                    """,
                    objectMapper.writeValueAsString(response)
            );
            return response;
        } catch (Exception ex) {
            log.error("""
                    \n[API ERROR]
                    Error: {}
                    """,
                    ex.getMessage()
            );
            throw ex;
        }
    }
}

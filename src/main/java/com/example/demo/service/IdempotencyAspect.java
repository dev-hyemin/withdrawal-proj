package com.example.demo.service;

import com.example.demo.dto.Idempotent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {

    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @Around("@annotation(idempotent)")
    public Object handle(ProceedingJoinPoint pjp, Idempotent idempotent) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();

        String transactionId = parseKey(idempotent.transactionId(), signature, args);
        long ttl = idempotent.ttl();

        boolean isFirst = idempotencyService.tryRegister(transactionId, ttl);

        if (!isFirst) {
            String cached = idempotencyService.getSavedResponse(transactionId);
            if (cached != null) {
                return ResponseEntity.ok(objectMapper.readValue(cached, Object.class));
            }
            return ResponseEntity.status(409).body("Duplicate request");
        }

        Object response = pjp.proceed();

        // 응답을 Redis에 저장
        String json = objectMapper.writeValueAsString(response);
        idempotencyService.saveResponse(transactionId, json, ttl);

        return response;
    }

    private String parseKey(String keyExpression, MethodSignature signature, Object[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return parser.parseExpression(keyExpression).getValue(context, String.class);
    }

}

package com.example.demo.service;

import com.example.demo.dto.Idempotent;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
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
                return objectMapper.readValue(cached, ApiResponse.class);
            }
            return ApiResponse.error(HttpStatus.CONFLICT, "Duplicate request");
        }

        String resJson;
        try {
            Object response = pjp.proceed();

            resJson = objectMapper.writeValueAsString(response);
            idempotencyService.saveResponse(transactionId, resJson, ttl);

            return response;
        } catch (CustomException ex) {
            ApiResponse errorResponse = ApiResponse.error(ex.getHttpStatus(), ex.getMessage());

            resJson = objectMapper.writeValueAsString(errorResponse);
            idempotencyService.saveResponse(transactionId, resJson, ttl);

            throw ex;

        } catch (Exception ex) {
            ApiResponse errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

            resJson = objectMapper.writeValueAsString(errorResponse);
            idempotencyService.saveResponse(transactionId, resJson, ttl);

            throw ex;
        }
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

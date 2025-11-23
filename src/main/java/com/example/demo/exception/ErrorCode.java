package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    WALLET_NOT_FOUND(HttpStatus.BAD_REQUEST, "wallet not found"),
    INSUFFICIENT_FUNDS(HttpStatus.BAD_REQUEST, "insufficient funds");

    private final HttpStatus httpStatus;
    private final String message;
}
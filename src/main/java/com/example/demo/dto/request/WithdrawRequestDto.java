package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WithdrawRequestDto {
    @NotBlank
    private String transactionId;
    @NotNull
    private BigDecimal amount;
}

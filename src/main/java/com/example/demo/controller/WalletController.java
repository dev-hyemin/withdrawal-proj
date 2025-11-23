package com.example.demo.controller;

import com.example.demo.dto.Idempotent;
import com.example.demo.dto.request.WithdrawRequestDto;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/{walletId}/withdraw")
    @Idempotent(transactionId = "#request.transactionId", ttl = 600)
    public ApiResponse withdraw(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9-]{10,36}$") String walletId,
                                @Valid @RequestBody WithdrawRequestDto request) {
        BigDecimal balance = walletService.withdraw(walletId, request);
        Map<String, Object> data = new HashMap<>();
        data.put("balance", balance);
        return ApiResponse.success(data);
    }
}

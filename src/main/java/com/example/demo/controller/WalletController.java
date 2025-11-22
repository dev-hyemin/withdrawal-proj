package com.example.demo.controller;

import com.example.demo.dto.Idempotent;
import com.example.demo.dto.request.WithdrawRequestDto;
import com.example.demo.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/{walletId}/withdraw")
    @Idempotent(transactionId = "#request.transactionId", ttl = 600)
    public ResponseEntity<HttpStatus> withdraw(@PathVariable String walletId, @Valid @RequestBody WithdrawRequestDto request) {
        walletService.withdraw(walletId, request);
        return ResponseEntity.ok().build();
    }
}

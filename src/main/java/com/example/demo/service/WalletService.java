package com.example.demo.service;

import com.example.demo.domain.models.BalanceEntity;
import com.example.demo.domain.models.TransactionHistoryEntity;
import com.example.demo.dto.request.WithdrawRequestDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final BalanceRepository balanceRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public void withdraw(String walletId, WithdrawRequestDto request) {

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        BalanceEntity wallet = balanceRepository.findByWalletIdForUpdate(walletId).orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("insufficient funds");
        }

        BigDecimal balance = wallet.getBalance().subtract(request.getAmount());
        wallet.setBalance(balance);
        wallet.setLastUpdateDtime(now);
        balanceRepository.save(wallet);

        TransactionHistoryEntity builder = TransactionHistoryEntity.builder()
                .walletId(walletId)
                .balance(balance)
                .amount(request.getAmount())
                .regDtime(now)
                .build();
        transactionHistoryRepository.save(builder);
    }
}

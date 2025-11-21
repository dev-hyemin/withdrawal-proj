package com.example.demo.service;

import com.example.demo.domain.models.BalanceEntity;
import com.example.demo.domain.models.TransactionHistoryEntity;
import com.example.demo.dto.request.WithdrawRequestDto;
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
    public void withdraw(Long walletId, WithdrawRequestDto withdrawRequestDto) {

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        BalanceEntity wallet = balanceRepository.findByWalletIdForUpdate(walletId).orElseThrow(() -> new IllegalArgumentException("wallet not found"));
        if (wallet.getBalance().compareTo(withdrawRequestDto.getAmount()) < 0) {
            throw new IllegalArgumentException("insufficient funds");
        }

        BigDecimal balance = wallet.getBalance().subtract(withdrawRequestDto.getAmount());
        wallet.setBalance(balance);
        wallet.setLastUpdateDtime(now);
        balanceRepository.save(wallet);

        TransactionHistoryEntity builder = TransactionHistoryEntity.builder()
                .walletId(walletId)
                .balance(balance)
                .amount(withdrawRequestDto.getAmount())
                .regDtime(now)
                .build();
        transactionHistoryRepository.save(builder);
    }
}

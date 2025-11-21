package com.example.demo.repository;

import com.example.demo.domain.models.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Object> {
    boolean existsByWalletId(Long walletId);
    BalanceEntity findByWalletId(Long walletId);
}

package com.example.demo.repository;

import com.example.demo.domain.models.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Object> {
    @Query(value = "SELECT * FROM balance WHERE wallet_id = :walletId FOR UPDATE", nativeQuery = true)
    Optional<BalanceEntity> findByWalletIdForUpdate(@Param("walletId") String walletId);
}

package com.example.demo.repository;

import com.example.demo.domain.models.BalanceEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Object> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select * from balance where walletId = :id")
    Optional<BalanceEntity> findByWalletIdForUpdate(@Param("id") Long walletId);
}

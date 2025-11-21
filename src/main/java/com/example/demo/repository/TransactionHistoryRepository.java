package com.example.demo.repository;

import com.example.demo.domain.models.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Object> {
}

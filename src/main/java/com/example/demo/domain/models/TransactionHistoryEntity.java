package com.example.demo.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id", length = 36, nullable = false)
    private String walletId;

    private BigDecimal amount;

    private BigDecimal balance;

    @Column(name = "reg_dtime")
    private LocalDateTime regDtime;
}

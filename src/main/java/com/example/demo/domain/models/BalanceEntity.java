package com.example.demo.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "balance")
public class BalanceEntity {

    @Id
    @Column(name = "wallet_id", length = 36, nullable = false, updatable = false)
    private String walletId;

    @Setter
    @Column(nullable = false)
    private BigDecimal balance;

    @Setter
    @Column(name = "last_update_dtime")
    private LocalDateTime lastUpdateDtime;
}
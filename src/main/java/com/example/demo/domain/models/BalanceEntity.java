package com.example.demo.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class BalanceEntity {

    @Id
    private Long walletId;
    private BigDecimal balance;
    private LocalDateTime lastUpdateDtime;
}

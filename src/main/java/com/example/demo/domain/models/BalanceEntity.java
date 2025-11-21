package com.example.demo.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance")
public class BalanceEntity {

    @Id
    private Long walletId;
    @Setter
    private BigDecimal balance;
    @Setter
    private LocalDateTime lastUpdateDtime;

}

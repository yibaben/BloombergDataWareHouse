package com.progressSoft.clusteredDataWarehouse.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "fx_deal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForexDeals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(name = "deal_id", unique = true, nullable = false)
    private String dealUniqueId;
    @Column(name = "from_currency", nullable = false)
    private Currency fromCurrencyISOCode;
    @Column(name = "to_currency", nullable = false)
    private Currency toCurrencyISOCode;
    @Column(name = "deal_timestamp", nullable = false)
    @CreationTimestamp
    private LocalDateTime dealTimeStamp;
    @Column(name = "amount_in_ordering_currency", nullable = false)
    private BigDecimal dealAmount;
}


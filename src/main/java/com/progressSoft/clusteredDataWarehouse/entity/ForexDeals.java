package com.progressSoft.clusteredDataWarehouse.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ForexDeals{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dealUniqueId;

    @Column(nullable = false)
    private Currency fromCurrencyISOCode;

    @Column(nullable = false)
    private Currency toCurrencyISOCode;

    @CreationTimestamp
    private LocalDateTime dealTimeStamp;

    @Column(nullable = false)
    private BigDecimal dealAmount;


}


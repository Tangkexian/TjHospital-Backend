package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseStatistic {
    private String medicineName;
    private String manufacturer;
    private int year;
    private int month;
    private BigDecimal purchaseAmount;
    private BigDecimal purchaseCost;
}
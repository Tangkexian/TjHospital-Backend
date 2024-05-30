package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellStatistic {
    private String medicineName;
    private String manufacturer;
    private int year;
    private int month;
    private BigDecimal sellAmount;
    private BigDecimal sellIncome;
}
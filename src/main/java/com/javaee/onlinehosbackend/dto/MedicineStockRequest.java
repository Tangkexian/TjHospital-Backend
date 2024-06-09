package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineStockRequest {
    private String medicineName;
    private String manufacturer;
    private LocalDate productionDate;
    private BigDecimal medicineShelflife;
    private BigDecimal medicineAmount;
    private BigDecimal thresholdValue;
    private String administratorId;
    private BigDecimal purchasePrice;
    private String medicineType;
    private String applicableSymptom;
    private String specification;
    private String singledose;
    private String administration;
    private String attention;
    private String frequency;
    private BigDecimal sellingprice;
}

package com.javaee.onlinehosbackend.dto;

import com.javaee.onlinehosbackend.entity.MedicineDescription;
import com.javaee.onlinehosbackend.entity.MedicineOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {
    private String medicineName;
    private String manufacturer;
    private BigDecimal sellingPrice;
    private String medicineType;
    private String applicableSymptom;
    private String vulgo;
    private String specification;
    private String singledose;
    private String administration;
    private String attention;
    private String frequency;
}

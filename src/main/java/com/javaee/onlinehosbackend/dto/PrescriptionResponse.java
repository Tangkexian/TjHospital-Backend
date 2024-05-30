package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

    private String prescriptionId;

    private LocalDateTime diagnoseTime;

    private String patientId;

    private BigDecimal totalPrice;

    private Integer paystate;

    private String DoctorId;
}

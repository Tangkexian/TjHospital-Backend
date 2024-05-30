package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetailResponse {
    private BigDecimal totalPrice;
    private DoctorInfo doctor;
    private DiagnoseInfo diagnose;
    private List<MedicineInfo> medicines;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorInfo {
        private String name;
        private String title;
        private String secondaryDepartment;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiagnoseInfo {
        private Instant diagnoseTime;
        private String anamnesis;
        private String sign;
        private String clinicdia;
        private String advice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineInfo {
        private String medicineName;
        private String medicationInstruction;
        private BigDecimal medicinePrice;
        private Integer quantity;
    }
}

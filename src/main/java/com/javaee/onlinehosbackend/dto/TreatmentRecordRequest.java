package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentRecordRequest {
    private String patientId;
    private String doctorId;
    private Integer period;
    private String selfReported;
    private String presentHis;
    private String anamnesis;
    private String sign;
    private String clinicDia;
    private String advice;
    private String medicine;
}

package com.javaee.onlinehosbackend.dto;

import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.entity.PrescriptionMedicine;
import com.javaee.onlinehosbackend.entity.TreatmentRecord2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailPreResponse {
    private Patient patientInfo;
    private List<TreatmentDetail> treatmentDetails;

    // 嵌套类，用于表示每个治疗记录的详细信息
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreatmentDetail {
        private TreatmentRecord2 treatmentRecord2;
        private List<PrescriptionMedicine> prescriptionMedicines;
        private Doctor doctor;
    }
}
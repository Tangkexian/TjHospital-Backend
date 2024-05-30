package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailResponse {
    private String patientId;
    private String name;
    private Boolean gender;
    private LocalDate birthDate;
    private String contact;
    private String college;
    private String counsellor;
    private List<TreatmentDetailDTO> treatmentDetails;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreatmentDetailDTO {
        private String diagnoseId;
        private String doctorId;
        private LocalDate diagnoseTime;
        private Integer commentState;
        // 其他与治疗记录相关的字段
    }
}

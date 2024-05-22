package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentFeedbacksResponse {
    String diagnosedId;
    String patientId;
    String doctorId;
    Integer treatmentScore;
    String evaluation;

    @Override
    public String toString() {
        return "TreatmentFeedbacksResponse{" +
                "diagnosedId='" + diagnosedId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", treatmentScore=" + treatmentScore +
                ", evaluation='" + evaluation + '\'' +
                '}';
    }
}
package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedbackRequest {
    private String diagnoseId;
    private Integer treatmentScore;
    private String evaluation;
}

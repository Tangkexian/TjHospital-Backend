package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInfoResponse {
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String doctorDepartment;
    private LocalDate appointmentDate;
    private Integer period;
    private Integer registrationFee;
    private Integer checkin;
}

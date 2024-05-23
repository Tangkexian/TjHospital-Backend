package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String patientId;
    private String doctorId;
    private LocalDateTime time;
    private Integer period;
    private String qrCodeUrl;
}
package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAppointmentRequest {
    private RegistrationRequest old;
    private NewRegistRequest newRegistRequest;
}

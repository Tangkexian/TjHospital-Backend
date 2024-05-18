package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanedMedicineResponse  {
    private String medicineName;
    private LocalDate cleanDate;
    private String administratorId;
    private String cleanAdministrator;
    private String manufacturer;
    private LocalDate productionDate;
}
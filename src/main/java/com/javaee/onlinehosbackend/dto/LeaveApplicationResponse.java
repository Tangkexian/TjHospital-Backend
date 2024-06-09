package com.javaee.onlinehosbackend.dto;

import com.javaee.onlinehosbackend.entity.LeaveApplication;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.entity.TreatmentRecord2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplicationResponse {

    private LeaveApplication leaveApplication;
    private Patient patient;
    private TreatmentRecord2 treatmentRecord2;

}

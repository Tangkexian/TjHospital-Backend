package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.entity.Prescription;
import com.javaee.onlinehosbackend.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Integer getPayState(String diagnosedId) {
        String prescriptionId = diagnosedId.substring(0, 8) + "000" + diagnosedId.substring(8);
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElse(null);
        if (prescription != null) {
            return prescription.getPaystate();
        }
        return null;
    }
}

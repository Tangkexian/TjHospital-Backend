package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.DetailPreResponse;
import com.javaee.onlinehosbackend.entity.TreatmentRecord2;
import com.javaee.onlinehosbackend.service.PatientService;
import com.javaee.onlinehosbackend.service.PaymentService;
import com.javaee.onlinehosbackend.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/DiagnosedHistory")
public class DiagnosedHistoryController {
    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/getPatientRecords")
    @CrossOrigin
    public ResponseEntity<?> getPatientRecords(@RequestParam String patientId) {
        List<TreatmentRecord2> records = treatmentService.getPatientRecords(patientId);
        if (records.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(records);
    }

    @GetMapping("/GetPaystate")
    @CrossOrigin
    public ResponseEntity<?> getPayState(@RequestParam String diagnosedId) {
        Integer payState = paymentService.getPayState(diagnosedId);
        if (payState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payState);
    }

    @GetMapping("/GetDetailPre")
    @CrossOrigin
    public ResponseEntity<?> getPatientDetails(@RequestParam String patientId) {
        DetailPreResponse response = patientService.getDetailedPrescription(patientId);
        return ResponseEntity.ok(response);
    }
}

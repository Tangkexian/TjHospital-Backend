package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.TreatmentRecordRequest;
import com.javaee.onlinehosbackend.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Confirm")
public class ConfirmController {
    @Autowired
    private TreatmentService treatmentService;

    @PostMapping
    @CrossOrigin
    public ResponseEntity<?> createTreatmentRecord(@RequestBody TreatmentRecordRequest treatmentRecordRequest) {
        try {
            String result = treatmentService.createTreatmentRecord(treatmentRecordRequest);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

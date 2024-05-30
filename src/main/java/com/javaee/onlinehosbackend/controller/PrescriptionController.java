package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.PrescriptionDetailResponse;
import com.javaee.onlinehosbackend.dto.PrescriptionResponse;
import com.javaee.onlinehosbackend.entity.Prescription;
import com.javaee.onlinehosbackend.entity.PrescriptionMedicine;
import com.javaee.onlinehosbackend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;


    @GetMapping("/GetPrescription")
    @CrossOrigin
    public ResponseEntity<?> getPrescriptionById(@RequestParam String diagnoseId) {
        PrescriptionDetailResponse prescriptionResponse = prescriptionService.getPrescriptionById(diagnoseId);
        if (prescriptionResponse != null) {
            return ResponseEntity.ok(prescriptionResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/GetAll")
    @CrossOrigin
    public ResponseEntity<?> getAllPrescriptions() {
        List<PrescriptionResponse> prescriptionResponseList = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptionResponseList);
    }

    @GetMapping("/GetDetail")
    @CrossOrigin
    public ResponseEntity<List<PrescriptionMedicine>> getPrescriptionDetails(@RequestParam String prescriptionId) {
        List<PrescriptionMedicine> prescriptionMedicines = prescriptionService.getPrescriptionMedicines(prescriptionId);
        if (prescriptionMedicines.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prescriptionMedicines);
    }

    @PutMapping("/UpdatePaystate")
    @CrossOrigin
    public ResponseEntity<?> updatePayState(@RequestParam String prescriptionId) {
        Optional<Prescription> updatedPrescription = prescriptionService.updatePayState(prescriptionId);
        if (updatedPrescription.isPresent()) {
            return ResponseEntity.ok("支付状态更新成功");
        } else {
            return ResponseEntity.badRequest().body("无法更新支付状态");
        }
    }
}

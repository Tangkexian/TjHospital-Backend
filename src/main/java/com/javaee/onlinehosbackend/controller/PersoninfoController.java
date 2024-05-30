package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/Personinfo")
public class PersoninfoController {
    @Autowired
    private PatientService patientService;

    @GetMapping()
    @CrossOrigin
    public ResponseEntity<?> getRegistrations(@RequestParam String PatientID) {
        Patient patient = patientService.getPatientById(PatientID);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/update")
    @CrossOrigin
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient) {
        String msg = patientService.updatePatient(patient);
        if (Objects.equals(msg, "Not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
}

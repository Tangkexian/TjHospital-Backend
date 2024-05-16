package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.entity.Administrator;
import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.service.AdministratorService;
import com.javaee.onlinehosbackend.service.DoctorService;
import com.javaee.onlinehosbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@RestController
@RequestMapping("/api/Enroll")
public class EnrollController {
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/AdminEnroll")
    @CrossOrigin
    public ResponseEntity<?> adminEnroll(@RequestBody Administrator admin) {
        if (administratorService.adminEnroll(admin)) {
            return ResponseEntity.ok("Enroll successfully.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This person has been in Database.");
    }

    @PostMapping("/DoctorEnroll")
    @CrossOrigin
    public ResponseEntity<?> doctorEnroll(@RequestBody Doctor doctor) {
        if (doctorService.doctorEnroll(doctor)) {
            return ResponseEntity.ok("Enroll successfully.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This person has been in Database.");
    }

    @PostMapping("/PatientEnroll")
    @CrossOrigin
    public ResponseEntity<?> patientEnroll(@RequestBody Patient patient) {
        if (patientService.patientEnroll(patient)) {
            return ResponseEntity.ok("Enroll successfully.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This person has been in Database.");
    }
}

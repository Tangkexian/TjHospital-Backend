package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.DoctorDetailResponse;
import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Doctors")
public class DoctorsController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/id")
    @CrossOrigin
    public ResponseEntity<?> getDoctorById(@RequestParam String id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/name")
    @CrossOrigin
    public ResponseEntity<?> getDoctorByName(@RequestParam String name) {
        List<Doctor> doctorList = doctorService.getDoctorByName(name);
        if (doctorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctorList);
    }

    @GetMapping("/dept")
    @CrossOrigin
    public ResponseEntity<?> getDoctorByDept(@RequestParam String dept) {
        List<Doctor> doctorList = doctorService.getDoctorByDept(dept);
        if (doctorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctorList);
    }

    @PostMapping("/add")
    @CrossOrigin
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        try {
            // 检查医生ID是否已存在
            if (doctorService.existsById(doctor.getDoctorId())) {
                return ResponseEntity.badRequest().body("Doctor with this ID already exists.");
            }

            Doctor savedDoctor = doctorService.addDoctor(doctor);
            return ResponseEntity.ok(savedDoctor);
        } catch (Exception e) {
            // 异常处理
            return ResponseEntity.internalServerError().body("Error adding doctor: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    @CrossOrigin
    public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor) {
        try {
            // 检查要更新的医生是否存在
            if (!doctorService.existsById(doctor.getDoctorId())) {
                return ResponseEntity.notFound().build();
            }

            Doctor updatedDoctor = doctorService.updateDoctor(doctor);
            return ResponseEntity.ok(updatedDoctor);
        } catch (Exception e) {
            // 异常处理
            return ResponseEntity.internalServerError().body("Error updating doctor: " + e.getMessage());
        }
    }

    @GetMapping("/fee")
    @CrossOrigin
    public ResponseEntity<?> getDoctorFeeAndConsultationInfo(@RequestParam String doctorId) {
        DoctorDetailResponse doctorDetails = doctorService.getDoctorDetails(doctorId);

        if (doctorDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(doctorDetails);
    }
}
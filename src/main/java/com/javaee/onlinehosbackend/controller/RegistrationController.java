package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.ChangeAppointmentRequest;
import com.javaee.onlinehosbackend.dto.Registration2Request;
import com.javaee.onlinehosbackend.dto.RegistrationInfoResponse;
import com.javaee.onlinehosbackend.dto.RegistrationRequest;
import com.javaee.onlinehosbackend.entity.Registration;
import com.javaee.onlinehosbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/Registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/GetAllRegist")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/GetRegist")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getRegistrationsByDoctorIdAndDate(
            @RequestParam String doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Registration> registrations = registrationService.getRegistrationsByDoctorIdAndDate(doctorId, date);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/GetRegist/{date}/{period}")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getRegistrationsByDateAndPeriod(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer period) {
        List<Registration> registrations = registrationService.getRegistrationsByDateAndPeriod(date, period);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/commit")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getTodayRegistrationsByDoctorId(@RequestParam String doctorId) {
        List<Registration> registrations = registrationService.getTodayRegistrationsByDoctorId(doctorId, 1); // 1 for checkin
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/Doctor/{ID}")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getRegistrationsByDoctorId(@PathVariable String ID) {
        List<Registration> registrations = registrationService.getRegistrationsByDoctorId(ID);
        if (registrations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/GetPatientInfoByDoctorID/{ID}")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getCompletedRegistrationsByDoctorId(@PathVariable String ID) {
        List<Registration> registrations = registrationService.getCompletedRegistrationsByDoctorId(ID);
        if (registrations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/Patient/{ID}")
    @CrossOrigin
    public ResponseEntity<List<Registration>> getRegistrationsByPatientId(@PathVariable String ID) {
        List<Registration> registrations = registrationService.getRegistrationsByPatientId(ID);
        if (registrations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @PostMapping("/regist")
    @CrossOrigin
    public ResponseEntity<?> createRegistration(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Registration newRegistration = registrationService.createRegistration(registrationRequest);
            // 短信发送逻辑...
            return ResponseEntity.ok(newRegistration);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel")
    @CrossOrigin
    public ResponseEntity<?> cancelRegistration(@RequestBody Registration2Request registration2Request) {
        try {
            Registration updatedRegistration = registrationService.cancelRegistration(registration2Request);
            return ResponseEntity.ok("Cancel successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/complete")
    @CrossOrigin
    public ResponseEntity<?> completeRegistration(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Registration updatedRegistration = registrationService.completeRegistration(registrationRequest);
            return ResponseEntity.ok("Complete successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/ChangeAppoint")
    @CrossOrigin
    public ResponseEntity<?> changeAppointmentTime(@RequestBody ChangeAppointmentRequest changeAppointmentRequest) {
        try {
            Registration updatedRegistration = registrationService.changeAppointmentTime(changeAppointmentRequest);
            return ResponseEntity.ok("Appointment time changed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/Checkin")
    @CrossOrigin
    public ResponseEntity<?> updateCheckin(@RequestBody Registration2Request registration2Request) {
        try {
            Registration updatedRegistration = registrationService.updateCheckin(registration2Request);
            return ResponseEntity.ok("报到成功");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/OfflineCheckin")
    @CrossOrigin
    public ResponseEntity<?> getRegistrationInfo(@RequestParam String patientId) {
        List<RegistrationInfoResponse> registrationInfo = registrationService.getTodaysRegistrationsForPatient(patientId);
        if (registrationInfo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationInfo);
    }
}

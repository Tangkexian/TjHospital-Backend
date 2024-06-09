package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.LeaveApplicationResponse;
import com.javaee.onlinehosbackend.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javaee.onlinehosbackend.entity.LeaveApplication;

import java.util.List;

@RestController
@RequestMapping("/api/Leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @PostMapping()
    @CrossOrigin
    public ResponseEntity<?> onlineApplication(@RequestParam String diagnosedId, @RequestParam int leaveDays) {
        String result = leaveService.createOnlineApplication(diagnosedId, leaveDays);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/Offline")
    @CrossOrigin
    public ResponseEntity<?> offlineApplication(@RequestParam String patientId, @RequestParam String doctorId, @RequestParam String period, @RequestParam int leaveDays) {
        String result = leaveService.createOfflineApplication(patientId, doctorId, period, leaveDays);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/ratify")
    @CrossOrigin
    public ResponseEntity<?> ratifyLeaveApplication(@RequestParam String leaveNoteId, @RequestParam int status) {
        String result = leaveService.ratifyLeaveApplication(leaveNoteId, status);

        if (result.equals("未找到该假条记录（或该假条已被审核）") || result.equals("Invalid status value")) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/LeaveApplications")
    @CrossOrigin
    public ResponseEntity<?> getLeaveApplicationsByPatient(@RequestParam String patientId) {
        List<LeaveApplication> leaveApplications = leaveService.getLeaveApplicationsByPatientId(patientId);

        if (leaveApplications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(leaveApplications);
    }

    @GetMapping("/diagnosis")
    @CrossOrigin
    public ResponseEntity<?> getDiagnosedId(@RequestParam String patientId) {
        List<String> diagnosedIds = leaveService.getDiagnosedIdsByPatientId(patientId);

        if (diagnosedIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(diagnosedIds);
    }

    @GetMapping("/audited")
    @CrossOrigin
    public ResponseEntity<?> getAuditedLeaveNoteId(@RequestParam String patientId) {
        List<String> auditedLeaveNoteIds = leaveService.getAuditedLeaveNoteIdsByPatientId(patientId);

        if (auditedLeaveNoteIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(auditedLeaveNoteIds);
    }

    @GetMapping("/GetLeaveApplicationsByDoctor")
    @CrossOrigin
    public ResponseEntity<?> getLeaveApplicationsByDoctor(@RequestParam String doctorId) {
        List<LeaveApplicationResponse> leaveApplications = leaveService.getLeaveApplicationsByDoctorId(doctorId);

        if (leaveApplications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(leaveApplications);
    }
}

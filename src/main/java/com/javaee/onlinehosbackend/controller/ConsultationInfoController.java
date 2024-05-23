package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.*;
import com.javaee.onlinehosbackend.entity.ConsultationInfo;
import com.javaee.onlinehosbackend.entity.ConsultingRoom;
import com.javaee.onlinehosbackend.service.ConsultationInfoService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/Consultationinfo")
public class ConsultationInfoController {
    @Autowired
    private ConsultationInfoService consultationInfoService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getConsultationInfoByDepartmentAndKeyword(@RequestParam String department, @RequestParam(required = false) String keyword) {
        List<DoctorConsultationInfoResponse> result = consultationInfoService.getConsultationInfoByDepartmentAndKeyword(department, keyword);
        if (result.isEmpty()) {
            return ResponseEntity.ok("empty");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/AllConsultInfo")
    @CrossOrigin
    public ResponseEntity<?> getAllConsultInfo() {
        List<ConsultationInfoResponse> responses = consultationInfoService.getAllConsultationInfos();
        if (responses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/ChangeConsult")
    @CrossOrigin
    public ResponseEntity<?> changeConsultInfo(@RequestBody ChangeConsultRequest changeConsultRequest) {
        String response = consultationInfoService.changeConsultInfo(changeConsultRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/CancelConsult")
    @CrossOrigin
    public ResponseEntity<?> cancelConsultInfo(@RequestBody ConsultCancelRequest consultCancelRequest) {
        String response = consultationInfoService.deleteConsultationInfo(consultCancelRequest);
        if (response.equals("No matching ConsultationInfo found to delete.")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/AddConsult")
    @CrossOrigin
    public ResponseEntity<?> addConsultInfo(@RequestBody ConsultRequest consultRequest) {
        try {
            String result = consultationInfoService.addConsultInfo(consultRequest);
            return ResponseEntity.ok(result);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while processing your request.");
        }
    }
    @GetMapping("/GetAllRooms")
    @CrossOrigin
    public ResponseEntity<?> getAllConsultingRooms() {
        List<ConsultingRoom> rooms = consultationInfoService.getAllConsultingRooms();
        if (rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms);
    }
}

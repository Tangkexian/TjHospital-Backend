package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.CleanedMedicineResponse;
import com.javaee.onlinehosbackend.dto.MedicineResponse;
import com.javaee.onlinehosbackend.dto.MedicineStatisticsResponse;
import com.javaee.onlinehosbackend.dto.MedicineStockRequest;
import com.javaee.onlinehosbackend.service.MedicineService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping("GetAllMedicinesInfo")
    @CrossOrigin
    public ResponseEntity<?> getAllMedicinesInfo() {
        List<MedicineResponse> medicineResponseList = medicineService.getAllMedicinesInfo();
        if (medicineResponseList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicineResponseList);
    }


    @PutMapping("UpdateStock")
    @CrossOrigin
    public ResponseEntity<?> UpdateStock(@RequestParam String medicineName, @RequestParam String manufacturer,
                                         @RequestParam(name = "productionDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate productionDate,
                                         @RequestParam BigDecimal newAmount, @RequestParam String patientId) {

        try {
            String response = medicineService.updateStock(medicineName, manufacturer, productionDate, newAmount, patientId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        }
    }

    @PostMapping("/AddStock")
    @CrossOrigin
    public ResponseEntity<?> addStock(@RequestBody MedicineStockRequest medicineStockRequest) {
        try {
            String result = medicineService.addStock(medicineStockRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("CleanMedicine")
    @CrossOrigin
    public ResponseEntity<?> cleanMedicine(@RequestParam String medicineName,
                                           @RequestParam String manufacturer,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate productionDate,
                                           @RequestParam String administratorId) {

        String result = medicineService.cleanMedicine(medicineName, manufacturer, productionDate, administratorId);

        if (result.equals("未找到该药品库存")) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/GetCleanedMedicines")
    @CrossOrigin
    public ResponseEntity<?> getCleanedMedicines() {
        List<CleanedMedicineResponse> cleanedMedicines = medicineService.getCleanedMedicines();
        return ResponseEntity.ok(cleanedMedicines);
    }

    @GetMapping("/GetMedicineStatistics")
    @CrossOrigin
    public ResponseEntity<MedicineStatisticsResponse> getMedicineStatistics() {
        MedicineStatisticsResponse statistics = medicineService.getMedicineStatistics();
        return ResponseEntity.ok(statistics);
    }
}
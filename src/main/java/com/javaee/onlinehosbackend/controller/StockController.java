package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.entity.MedicineStock;
import com.javaee.onlinehosbackend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Stock")
public class StockController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/GetAllStocks")
    @CrossOrigin
    public ResponseEntity<?> getMedicineStocks() {
        List<MedicineStock> stocks = medicineService.getAllMedicineStocks();
        return ResponseEntity.ok(stocks);
    }
}

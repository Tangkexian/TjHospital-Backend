package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.PatientDetailResponse;
import com.javaee.onlinehosbackend.entity.TreatmentRecord;
import com.javaee.onlinehosbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @PostMapping("/PostTreatmentRecord1")
    @CrossOrigin
    public ResponseEntity<?> postTreatmentRecord(@RequestBody TreatmentRecord treatmentRecord) {
        try {
            // 参数校验
            if (treatmentRecord == null || treatmentRecord.getDoctor().getDoctorId() == null || treatmentRecord.getPatient().getPatientId() == null) {
                return ResponseEntity.badRequest().body("Invalid Treatment Record data");
            }

            // 调用服务层进行业务处理
            TreatmentRecord savedRecord = patientService.createTreatmentRecord(treatmentRecord);

            // 返回成功响应
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
        } catch (Exception e) {
            // 异常处理
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving Treatment Record: " + e.getMessage());
        }
    }

    @GetMapping("/GetAllTreatmentRecord1")
    @CrossOrigin
    public ResponseEntity<?> getAllTreatmentRecords() {
        try {
            // 调用服务层获取数据
            List<TreatmentRecord> treatmentRecords = patientService.getAllTreatmentRecords();

            // 确保返回的列表不为空
            if (treatmentRecords.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // 返回成功响应
            return ResponseEntity.ok(treatmentRecords);
        } catch (Exception e) {
            // 异常处理
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving Treatment Records: " + e.getMessage());
        }
    }

    @GetMapping("/PatientDetails/{patientId}")
    @CrossOrigin
    public ResponseEntity<?> getPatientDetails(@PathVariable String patientId) {
        try {
            // 调用服务层获取数据
            Optional<PatientDetailResponse> patientDetails = patientService.getPatientDetails(patientId);

            // 检查是否找到了对应的病人详情
            if (!patientDetails.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // 返回成功响应
            return ResponseEntity.ok(patientDetails.get());
        } catch (Exception e) {
            // 异常处理
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving patient details: " + e.getMessage());
        }
    }

}

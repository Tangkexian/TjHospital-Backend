package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.CreateFeedbackRequest;
import com.javaee.onlinehosbackend.dto.TreatmentFeedbacksResponse;
import com.javaee.onlinehosbackend.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Comment")
public class CommentController {

    @Autowired
    private TreatmentService treatmentService; // 假设存在一个处理业务逻辑的服务类

    //找到该病人已经评价过的诊断记录ID
    @GetMapping("/whether")
    @CrossOrigin
    public ResponseEntity<?> getTreatmentRecord(@RequestParam String patientId) {
        // 使用 patientId 调用服务层获取数据
        List<String> records = treatmentService.getRecordsByPatientId(patientId);

        // 根据获取的记录返回适当的响应
        if (records.isEmpty()) {
            return ResponseEntity.ok().body("No records found for this patientId.");
        }

        return ResponseEntity.ok().body(records);
    }

    //获取所有反馈信息
    @GetMapping("/GetAllFeedbacks")
    @CrossOrigin
    public ResponseEntity<?> getAllFeedbacks()
    {
        List<TreatmentFeedbacksResponse> feedbacks = treatmentService.getAllFeedbacks().stream()
                .map(tf -> new TreatmentFeedbacksResponse(
                        tf.getDiagnosedid(),
                        tf.getPatientId(),
                        tf.getDoctorId(),
                        tf.getTreatmentScore(),
                        tf.getEvaluation()
                ))
                .collect(Collectors.toList());
//        System.out.println(feedbacks);
        return ResponseEntity.ok().body(feedbacks);
    }

    @DeleteMapping("/DeleteFeedback")
    @CrossOrigin
    public ResponseEntity<?> deleteFeedback(@RequestParam String diagnosedId) {
        try {
            treatmentService.deleteFeedback(diagnosedId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/CreateFeedback")
    @CrossOrigin
    public ResponseEntity<?> createFeedback(@RequestBody CreateFeedbackRequest request) {
        try {
            treatmentService.createFeedback(request.getDiagnoseId(), request.getTreatmentScore(), request.getEvaluation());
            return ResponseEntity.ok("Treatment feedback created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

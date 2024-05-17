package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.ChatRecordRequest;
import com.javaee.onlinehosbackend.entity.*;
import com.javaee.onlinehosbackend.service.ChatrecordService;
import com.javaee.onlinehosbackend.service.DoctorService;
import com.javaee.onlinehosbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/Chatrecord")
public class ChatrecordController {

    @Autowired
    private ChatrecordService chatrecordService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/getChatRecord")
    @CrossOrigin
    public ResponseEntity<?> getChatRecord(@RequestParam String RecordId) {
        List<Chatrecord> chatRecords = chatrecordService.getChatRecords(RecordId);
        if (chatRecords.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chatRecords);
    }

    @PostMapping("/addChatRecord")
    @CrossOrigin
    public ResponseEntity<?> addChatRecord(@RequestBody ChatRecordRequest chatRecordRequest) {
        ChatrecordId newChatrecordId = new ChatrecordId();
        newChatrecordId.setDoctorId(chatRecordRequest.getDoctorId());
        newChatrecordId.setPatientId(chatRecordRequest.getPatientId());
        newChatrecordId.setTimestamp(chatRecordRequest.getTimeStamp());

        // 检查是否已存在相同的聊天记录
        if (chatrecordService.existsById(newChatrecordId)) {
            return ResponseEntity.badRequest().body("Chatrecord already exists.");
        }

        Chatrecord newRecord = new Chatrecord();
        newRecord.setId(newChatrecordId); // 设置复合主键
        newRecord.setRecordid(chatRecordRequest.getRecordId());
        newRecord.setMessage(chatRecordRequest.getMessage());
        newRecord.setSenderType(chatRecordRequest.getSenderType());
        newRecord.setReadStatus(chatRecordRequest.getReadStatus());

        // 设置 Doctor 和 Patient 关联实体
        newRecord.setDoctor(doctorService.getDoctorById(chatRecordRequest.getDoctorId()));
        newRecord.setPatient(patientService.getPatientById(chatRecordRequest.getPatientId()));

        Chatrecord savedRecord = chatrecordService.addChatRecord(newRecord);
        return ResponseEntity.ok(savedRecord);
    }

}

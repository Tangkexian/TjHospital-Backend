package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecordRequest {
    private String recordId;
    private String doctorId;
    private String patientId;
    private String message;
    private Integer senderType;
    private LocalDateTime timeStamp;
    private Integer readStatus;
}

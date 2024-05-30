package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationInfoResponse {
    private String doctorId;
    private String doctorName;
    private String clinicName;
    private LocalDateTime date; // 格式化为所需日期格式
    private String startTime;
    private String endTime;
}

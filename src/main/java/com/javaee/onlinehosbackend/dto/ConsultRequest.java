package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultRequest {
    private String doctorId;
    private String clinicName;
    private LocalDateTime dateTime;
    private Integer period;

    // 转换 LocalDateTime 到 Instant
    public Instant getInstantDateTime() {
        System.out.println(this.dateTime);
        return dateTime.atZone(ZoneId.of("UTC")).toInstant();
    }
}

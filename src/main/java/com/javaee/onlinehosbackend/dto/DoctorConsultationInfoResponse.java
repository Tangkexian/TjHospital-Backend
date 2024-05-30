package com.javaee.onlinehosbackend.dto;

import com.javaee.onlinehosbackend.entity.ConsultationInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorConsultationInfoResponse {
    private String doctorId;
    private String doctorName;
    private String photoUrl;
    private String title;
    private String skilledIn;
    private List<ConsultationInfo> consultationInfos;
    // 嵌套类用于表示单个咨询信息
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsultationInfo {
        private String clinicName;
        private String dateTime;
        private Integer period;
    }
}

package com.javaee.onlinehosbackend.dto;

import com.javaee.onlinehosbackend.entity.ConsultationInfo;
import com.javaee.onlinehosbackend.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailResponse {
    // 医生实体
    private Doctor doctor;
    // 挂号费
    private int registrationFee;
    // 门诊信息列表
    private List<ConsultationInfo> consultationInfos;
}

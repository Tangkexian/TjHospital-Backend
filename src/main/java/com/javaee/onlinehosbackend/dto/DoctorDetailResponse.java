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
    private Doctor doctor; // 医生实体
    private int registrationFee; // 挂号费
    private List<ConsultationInfo> consultationInfos; // 门诊信息列表
}

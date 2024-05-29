package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.DoctorDetailResponse;
import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.repository.ConsultationInfoRepository;
import com.javaee.onlinehosbackend.repository.DoctorRepository;
import com.javaee.onlinehosbackend.utils.TokenGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultationInfoRepository consultationInfoRepository;

    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    public boolean validateDoctorCredentials(String id, String password) {
        return doctorRepository.existsByDoctorIdAndPassword(id, password);
    }

    public boolean validateDoctorPhoneID(String phoneNumber, String adminId) {
        return doctorRepository.existsByContactAndDoctorId(phoneNumber, adminId);
    }

    @Transactional
    public boolean resetDoctorPassword(String adminId, String newPassword) {
        Doctor doctor = doctorRepository.findById(adminId).orElse(null);
        if (doctor != null) {
            doctor.setPassword(newPassword);
            // 假设 setPassword 方法用于更新密码
            doctorRepository.save(doctor);
            return true;
        }
        return false;
    }

    public boolean existsById(String doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorByName(String name) {
        return doctorRepository.findByName(name);
    }

    public List<Doctor> getDoctorByDept(String dept) {
        return doctorRepository.findBySecondaryDepartment(dept);
    }

    @Transactional
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDoctor(Doctor doctor) {
        // 假设更新逻辑正确，直接保存
        return doctorRepository.save(doctor);
    }

    public DoctorDetailResponse getDoctorDetails(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return null;
        }

        DoctorDetailResponse response = new DoctorDetailResponse();
        response.setDoctor(doctor);
        response.setRegistrationFee(calculateRegistrationFee(doctor.getTitle()));
        response.setConsultationInfos(consultationInfoRepository.findByDoctorId(doctorId));

        return response;
    }

    private int calculateRegistrationFee(String title) {
        // 根据医生的职称计算挂号费用
        return switch (title) {
            case "主任医师" -> 9;
            case "副主任医师" -> 7;
            case "主治医师" -> 6;
            case "住院医师" -> 4;
            case "医师" -> 4;
            default -> 6;
        };
    }

    @Transactional
    public boolean doctorEnroll(Doctor doctor) {
        if (!doctorRepository.existsByDoctorId(doctor.getDoctorId())) {
            doctorRepository.save(doctor);
            return true;
        }
        return false;
    }
}

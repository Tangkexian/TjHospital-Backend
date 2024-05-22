package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.*;
import com.javaee.onlinehosbackend.entity.ConsultationInfo;
import com.javaee.onlinehosbackend.entity.ConsultationInfoId;
import com.javaee.onlinehosbackend.entity.ConsultingRoom;
import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.repository.ConsultationInfoRepository;
import com.javaee.onlinehosbackend.repository.ConsultingRoomRepository;
import com.javaee.onlinehosbackend.repository.DoctorRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationInfoService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultationInfoRepository consultationInfoRepository;

    @Autowired
    private ConsultingRoomRepository consultingRoomRepository;

    public List<DoctorConsultationInfoResponse> getConsultationInfoByDepartmentAndKeyword(String department, String keyword) {
        List<Doctor> doctors = null;
        if(keyword != null){
            if (department.equals("0")){
                doctors =  doctorRepository.findByName(keyword);
            }else {
                doctors =  doctorRepository.findBySecondaryDepartmentAndNameContaining(department, keyword);
            }
        }else {
            doctors =  doctorRepository.findBySecondaryDepartment(department);
        }
        List<DoctorConsultationInfoResponse> responses = new ArrayList<>();

        for (Doctor doctor : doctors) {
            List<ConsultationInfo> consultationInfos = consultationInfoRepository.findByDoctorId(doctor.getDoctorId());

            DoctorConsultationInfoResponse response = new DoctorConsultationInfoResponse();
            response.setDoctorId(doctor.getDoctorId());
            response.setDoctorName(doctor.getName());
            response.setPhotoUrl(doctor.getPhotourl());
            response.setTitle(doctor.getTitle());
            response.setSkilledIn(doctor.getSkilledin());
            response.setConsultationInfos(consultationInfos.stream().map(consultationInfo -> {
                DoctorConsultationInfoResponse.ConsultationInfo ci = new DoctorConsultationInfoResponse.ConsultationInfo();
                ci.setClinicName(consultationInfo.getClinicName().getConsultingRoomName());
                ci.setDateTime(consultationInfo.getId().getDateTime().toString());
                ci.setPeriod(consultationInfo.getId().getPeriod());
                return ci;
            }).collect(Collectors.toList()));

            responses.add(response);
        }

        return responses;
    }

    public List<ConsultationInfoResponse> getAllConsultationInfos() {
        List<ConsultationInfo> consultationInfos = consultationInfoRepository.findAll();

        return consultationInfos.stream().map(consultationInfo -> {
            ConsultationInfoResponse response = new ConsultationInfoResponse();
            response.setDoctorId(consultationInfo.getDoctor().getDoctorId());
            response.setDoctorName(consultationInfo.getDoctor().getName());
            response.setClinicName(consultationInfo.getId().getClinicName());
            response.setDate(convertToDateTime(consultationInfo.getId().getDateTime()));
            response.setStartTime(getStartTime(consultationInfo.getId().getPeriod()));
            response.setEndTime(getEndTime(consultationInfo.getId().getPeriod()));
            return response;
        }).collect(Collectors.toList());
    }

    private LocalDateTime convertToDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private String getStartTime(int period) {
        switch (period) {
            case 1:
                return "08:00";
            case 2:
                return "09:00";
            case 3:
                return "10:00";
            case 4:
                return "13:00";
            case 5:
                return "14:00";
            case 6:
                return "15:00";
            case 7:
                return "16:00";
            default:
                return "Unknown";
        }
    }

    private String getEndTime(int period) {
        switch (period) {
            case 1:
                return "09:00";
            case 2:
                return "10:00";
            case 3:
                return "11:00";
            case 4:
                return "14:00";
            case 5:
                return "15:00";
            case 6:
                return "16:00";
            case 7:
                return "17:00";
            default:
                return "Unknown";
        }
    }

    @Transactional
    public String changeConsultInfo(ChangeConsultRequest changeConsultRequest) {
        ConsultationInfoId oldId = createConsultationInfoId(changeConsultRequest.getOld());
        ConsultationInfo oldConsultationInfo = consultationInfoRepository.findById(oldId)
                .orElseThrow(() -> new EntityNotFoundException("No Changable ConsultationInfo found."));

        ConsultationInfoId newId = createConsultationInfoId(changeConsultRequest.getNew());
        if (consultationInfoRepository.existsById(newId)) {
            throw new EntityExistsException("ConsultationInfo with new ID already exists.");
        }
        Doctor newDoctor = doctorRepository.findById(changeConsultRequest.getNew().getDoctorId()).orElse(null);
        ConsultingRoom newConsultingRoom =  consultingRoomRepository.findById(changeConsultRequest.getNew().getClinicName()).orElse(null);
        ConsultationInfo newConsultationInfo = new ConsultationInfo(newId, newDoctor, newConsultingRoom);

        consultationInfoRepository.delete(oldConsultationInfo);
        consultationInfoRepository.save(newConsultationInfo);
//        System.out.println(newConsultationInfo.getDoctor().getDoctorId());

        return "Change ConsultationInfo Successfully.";
    }

    private ConsultationInfoId createConsultationInfoId(ChangeConsultRequest.ConsultInputModel data) {
        return new ConsultationInfoId(data.getDoctorId(), data.getClinicName(), data.getInstantDateTime(), data.getPeriod());
    }

    @Transactional
    public String addConsultInfo(ConsultRequest consultRequest) {
        ConsultationInfoId newConsultationInfoId = new ConsultationInfoId(
                consultRequest.getDoctorId(),
                consultRequest.getClinicName(),
                consultRequest.getInstantDateTime(),
                consultRequest.getPeriod()
        );
        // 检查是否存在相同的咨询记录
        boolean exists = consultationInfoRepository.existsById(newConsultationInfoId);

        if (exists) {
            throw new EntityExistsException("ConsultationInfo already exists.");
        }

        if (consultRequest.getPeriod() < 1 || consultRequest.getPeriod() > 7) {
            throw new IllegalArgumentException("Illegal Period.");
        }

        System.out.println(newConsultationInfoId);
        // 创建新的咨询记录实体
        ConsultationInfo newConsultationInfo = new ConsultationInfo();
        newConsultationInfo.setDoctor(doctorRepository.findById(consultRequest.getDoctorId()).orElseThrow(() -> new EntityNotFoundException("Doctor not found")));
        newConsultationInfo.setId(newConsultationInfoId);
        newConsultationInfo.setClinicName(consultingRoomRepository.findById(consultRequest.getClinicName()).orElseThrow(() -> new EntityNotFoundException("Consulting Room not found")));
        consultationInfoRepository.save(newConsultationInfo);

        return "ConsultationInfo added successfully.";
    }

    public List<ConsultingRoom> getAllConsultingRooms() {
        return consultingRoomRepository.findAll();
    }

    @Transactional
    public String deleteConsultationInfo(ConsultCancelRequest consultCancelRequest) {
        // 检查是否存在对应的实体
        boolean exists = consultationInfoRepository.existsByDoctorIdAndDateTimeAndPeriod(consultCancelRequest.getDoctorId(), consultCancelRequest.getInstantDateTime(), consultCancelRequest.getPeriod());

        if (!exists) {
            // 没有找到实体，返回错误消息或抛出异常
            return "No matching ConsultationInfo found to delete.";
        }

        // 存在实体，执行删除操作
        consultationInfoRepository.deleteByDoctorIdAndDateTimeAndPeriod(consultCancelRequest.getDoctorId(), consultCancelRequest.getInstantDateTime(), consultCancelRequest.getPeriod());
        return "ConsultationInfo deleted successfully.";
    }
}

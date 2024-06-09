package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.LeaveApplicationResponse;
import com.javaee.onlinehosbackend.dto.PatientResponse;
import com.javaee.onlinehosbackend.entity.LeaveApplication;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.entity.TreatmentRecord;
import com.javaee.onlinehosbackend.entity.TreatmentRecord2;
import com.javaee.onlinehosbackend.repository.LeaveRepository;
import com.javaee.onlinehosbackend.repository.PatientRepository;
import com.javaee.onlinehosbackend.repository.TreatmentRecord2Repository;
import com.javaee.onlinehosbackend.repository.TreatmentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;

    @Autowired
    private TreatmentRecord2Repository treatmentRecord2Repository;

    @Autowired
    private PatientRepository patientRepository;
    public String createOnlineApplication(String diagnosedId, int leaveDays) {
        if (leaveDays == 0) {
            return "No need for leave";
        }

        String leaveNoteId = diagnosedId + String.format("%03d", leaveDays);
        LocalDate leaveStartDate = LocalDate.parse(diagnosedId.substring(0, 8), DateTimeFormatter.BASIC_ISO_DATE);
        Instant leaveStartInstant = leaveStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant leaveEndInstant = leaveStartDate.plusDays(leaveDays).atStartOfDay(ZoneId.systemDefault()).toInstant();

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setLeaveNoteId(leaveNoteId);
        leaveApplication.setLeaveApplicationTime(Instant.now());
        leaveApplication.setLeaveStartDate(leaveStartInstant);
        leaveApplication.setLeaveEndDate(leaveEndInstant);
        leaveApplication.setLeaveNoteRemark("未审核");

        leaveRepository.save(leaveApplication);

        return "Leave application created successfully.";
    }

    @Transactional
    public String createOfflineApplication(String patientId, String doctorId, String period, int leaveDays) {
        if (leaveDays == 0) {
            return "No need for leave";
        }

        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String diagnosedId = today + patientId + doctorId + period;
        String leaveNoteId = today + patientId + doctorId + period + String.format("%03d", leaveDays);

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setLeaveNoteId(leaveNoteId);
        leaveApplication.setLeaveApplicationTime(Instant.now());
        leaveApplication.setLeaveStartDate(Instant.now());
        leaveApplication.setLeaveEndDate(Instant.now().plusSeconds(leaveDays * 86400)); // 86400 seconds in a day
        leaveApplication.setLeaveNoteRemark("通过");

        leaveRepository.save(leaveApplication);

        // 更新 TreatmentRecord 实体
        TreatmentRecord treatmentRecord = treatmentRecordRepository.findByDiagnosisRecordId(diagnosedId);
        if (treatmentRecord != null) {
            treatmentRecord.setLeaveNote(leaveApplication);
            treatmentRecordRepository.save(treatmentRecord);
        } else {
            return "未找到相关就诊记录，假条开具失败";
        }

        return "Leave application created successfully.";
    }

    @Transactional
    public String ratifyLeaveApplication(String leaveNoteId, int status) {
        LeaveApplication leaveApplication = leaveRepository.findById(leaveNoteId)
                .orElse(null);

        if (leaveApplication == null || !"未审核".equals(leaveApplication.getLeaveNoteRemark())) {
            return "未找到该假条记录（或该假条已被审核）";
        }

        if (status == 1) {
            leaveApplication.setLeaveNoteRemark("通过");
            // 更新对应的诊断记录
            String diagnosedId = leaveNoteId.substring(0, leaveNoteId.length() - 3);
            TreatmentRecord treatmentRecord = treatmentRecordRepository.findById(diagnosedId)
                    .orElse(null);
            if (treatmentRecord != null) {
                treatmentRecord.setLeaveNote(leaveApplication);
                treatmentRecordRepository.save(treatmentRecord);
            }
        } else if (status == 0) {
            leaveApplication.setLeaveNoteRemark("不通过");
        } else {
            return "Invalid status value";
        }

        leaveRepository.save(leaveApplication);
        return "Certificate of Approval Successful";
    }

    public List<LeaveApplication> getLeaveApplicationsByPatientId(String patientId) {
        return leaveRepository.findByPatientId(patientId);
    }

    public List<String> getDiagnosedIdsByPatientId(String patientId) {
        return leaveRepository.findDiagnosedIdsByPatientId(patientId);
    }

    public List<String> getAuditedLeaveNoteIdsByPatientId(String patientId) {
        return leaveRepository.findAuditedLeaveNoteIdsByPatientId(patientId);
    }

    public List<LeaveApplicationResponse> getLeaveApplicationsByDoctorId(String doctorId) {
        List<LeaveApplication> applications = leaveRepository.findUnauditedLeaveApplicationsByDoctorId(doctorId);
        List<LeaveApplicationResponse> details = new ArrayList<>();

        for (LeaveApplication application : applications) {
            String diagnosedId = application.getLeaveNoteId().substring(0, application.getLeaveNoteId().length() - 3);
            Patient patient = patientRepository.findById(diagnosedId.substring(8, 15)).orElse(null);
            TreatmentRecord2 treatmentRecord = treatmentRecord2Repository.findById(diagnosedId).orElse(null);

            if (patient != null && treatmentRecord != null) {
                LeaveApplicationResponse detail = new LeaveApplicationResponse(application, patient, treatmentRecord);
                details.add(detail);
            }
        }

        return details;
    }
}

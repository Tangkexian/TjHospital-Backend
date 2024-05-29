package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.DetailPreResponse;
import com.javaee.onlinehosbackend.dto.PatientDetailResponse;
import com.javaee.onlinehosbackend.entity.*;
import com.javaee.onlinehosbackend.repository.*;
import com.javaee.onlinehosbackend.utils.TokenGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;
    @Autowired
    private TreatmentRecord2Repository treatmentRecord2Repository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Autowired
    private MedicineDescriptionRepository medicineDescriptionRepository;

    public Patient getPatientById(String patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public boolean validatePatientCredentials(String id, String password) {
        return patientRepository.existsByPatientIdAndPassword(id, password);
    }

    public boolean validatePatientPhoneID(String phoneNumber, String adminId) {
        return patientRepository.existsByContactAndPatientId(phoneNumber, adminId);
    }

    public boolean resetPatientPassword(String adminId, String newPassword) {
        Patient patient = patientRepository.findById(adminId).orElse(null);
        if (patient != null) {
            patient.setPassword(newPassword); // 假设 setPassword 方法用于更新密码
            patientRepository.save(patient);
            return true;
        }
        return false;
    }

    public DetailPreResponse getDetailedPrescription(String patientId) {
        // 获取与病人ID匹配的所有治疗记录
        List<TreatmentRecord> treatmentRecords = treatmentRecordRepository.findByPatientId(patientId);

        if (treatmentRecords.isEmpty()) {
            return null;
        }

        // 获取所有的诊断记录ID
        List<String> diagnosisRecordIds = treatmentRecords.stream()
                .map(TreatmentRecord::getDiagnosisRecordId)
                .collect(Collectors.toList());

        // 获取所有诊断记录的详细信息
        List<TreatmentRecord2> treatmentRecord2s = treatmentRecord2Repository.findAllById(diagnosisRecordIds);

        // 获取所有处方ID
        List<String> prescriptionIds = diagnosisRecordIds.stream()
                .map(id -> id.substring(0, 8) + "000" + id.substring(8))
                .collect(Collectors.toList());

        // 获取所有处方药
        List<PrescriptionMedicine> prescriptionMedicines = prescriptionMedicineRepository.findByPrescriptionIdIn(prescriptionIds);

        // 获取所有药品名称
        List<String> medicineNames = prescriptionMedicines.stream()
                .map(pm -> pm.getMedicineName().getMedicineName())
                .distinct()
                .collect(Collectors.toList());

        // 获取所有药品描述
        List<MedicineDescription> medicineDescriptions = medicineDescriptionRepository.findByMedicineNameIn(medicineNames);

        // 获取所有相关医生信息
        List<String> doctorIds = treatmentRecord2s.stream()
                .map(tr2 -> tr2.getTreatmentRecord().getDoctor().getDoctorId())
                .collect(Collectors.toList());
        List<Doctor> doctors = doctorRepository.findAllById(doctorIds);

        // 构建返回的详细处方响应
        DetailPreResponse detailPreResponse = new DetailPreResponse();
        detailPreResponse.setPatientInfo(patientRepository.findById(patientId).orElse(null));

        List<DetailPreResponse.TreatmentDetail> treatmentDetails = new ArrayList<>();
        for (TreatmentRecord2 tr2 : treatmentRecord2s) {
            DetailPreResponse.TreatmentDetail treatmentDetail = new DetailPreResponse.TreatmentDetail();
            treatmentDetail.setTreatmentRecord2(tr2);
            treatmentDetail.setPrescriptionMedicines(prescriptionMedicines.stream()
                    .filter(pm -> pm.getPrescription().getPrescriptionId().equals(tr2.getTreatmentRecord().getDiagnosisRecordId()))
                    .collect(Collectors.toList()));
            treatmentDetail.setDoctor(doctors.stream()
                    .filter(d -> d.getDoctorId().equals(tr2.getTreatmentRecord().getDoctor().getDoctorId()))
                    .findFirst()
                    .orElse(null));
            treatmentDetails.add(treatmentDetail);
        }

        detailPreResponse.setTreatmentDetails(treatmentDetails);

        return detailPreResponse;
    }

    @Transactional
    public boolean patientEnroll(Patient patient) {
        if (!patientRepository.existsByPatientId(patient.getPatientId())) {
            patientRepository.save(patient);
            return true;
        }
        return false;
    }

    @Transactional
    public TreatmentRecord createTreatmentRecord(TreatmentRecord treatmentRecord) {
        // 实际的业务逻辑
        return treatmentRecordRepository.save(treatmentRecord);
    }

    public List<TreatmentRecord> getAllTreatmentRecords() {
        // 从数据库获取所有就诊记录
        return treatmentRecordRepository.findAll();
    }

    public Optional<PatientDetailResponse> getPatientDetails(String patientId) {
        // 查找病人信息
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return Optional.empty();
        }

        // 获取该病人的所有就诊记录
        List<TreatmentRecord> treatmentRecords = treatmentRecordRepository.findByPatientId(patientId);

        // 为每个就诊记录获取详细信息
        List<PatientDetailResponse.TreatmentDetailDTO> treatmentDetails = treatmentRecords.stream().map(record -> {
            TreatmentRecord2 treatmentRecord2 = treatmentRecord2Repository.findById(record.getDiagnosisRecordId()).orElse(null);
            if (treatmentRecord2 != null) {
                return new PatientDetailResponse.TreatmentDetailDTO(
                        treatmentRecord2.getDiagnoseId(),
                        record.getDoctor().getDoctorId(),
                        treatmentRecord2.getDiagnoseTime().atZone(ZoneId.systemDefault()).toLocalDate(),
                        treatmentRecord2.getCommentstate()
                );
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        // 封装并返回病人详情
        PatientDetailResponse patientDetails = new PatientDetailResponse(
                patient.getPatientId(),
                patient.getName(),
                patient.getGender(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getCollege(),
                patient.getCounsellor(),
                treatmentDetails
        );

        return Optional.of(patientDetails);
    }

    public String updatePatient(Patient patient) {
        Patient oldPatient = patientRepository.findById(patient.getPatientId()).orElse(null);
        if (oldPatient == null) {
            return "Not found";
        }
        patientRepository.save(patient);
        return "Successful!";
    }
}

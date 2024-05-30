package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.PrescriptionDetailResponse;
import com.javaee.onlinehosbackend.dto.PrescriptionResponse;
import com.javaee.onlinehosbackend.entity.*;
import com.javaee.onlinehosbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;

    @Autowired
    private TreatmentRecord2Repository treatmentRecord2Repository;

    @Autowired
    private DoctorRepository doctorRepository;


    public PrescriptionDetailResponse getPrescriptionById(String diagnoseId) {
        String prescriptionId = diagnoseId.substring(0, 8) + "000" + diagnoseId.substring(8);
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElse(null);

        if (prescription == null) {
            return null;
        }

        Doctor doctor = doctorRepository.findById(prescription.getDoctor().getDoctorId()).orElse(null);
        TreatmentRecord2 diagnose = treatmentRecord2Repository.findById(diagnoseId).orElse(null);

        if (diagnose == null || doctor == null) {
            return null;
        }

        // 获取药品信息列表
        List<PrescriptionMedicine> medicines = prescriptionMedicineRepository.findByPrescriptionPrescriptionId(prescriptionId);

        // 构造 PrescriptionDetailResponse
        PrescriptionDetailResponse response = new PrescriptionDetailResponse();
        response.setTotalPrice(prescription.getTotalPrice());

        PrescriptionDetailResponse.DoctorInfo doctorInfo = new PrescriptionDetailResponse.DoctorInfo();
        doctorInfo.setName(doctor.getName());
        doctorInfo.setTitle(doctor.getTitle());
        doctorInfo.setSecondaryDepartment(doctor.getSecondaryDepartment());
        response.setDoctor(doctorInfo);

        PrescriptionDetailResponse.DiagnoseInfo diagnoseInfo = new PrescriptionDetailResponse.DiagnoseInfo();
        diagnoseInfo.setDiagnoseTime(diagnose.getDiagnoseTime());
        diagnoseInfo.setAnamnesis(diagnose.getAnamnesis());
        diagnoseInfo.setSign(diagnose.getSign());
        diagnoseInfo.setClinicdia(diagnose.getClinicdia());
        diagnoseInfo.setAdvice(diagnose.getAdvice());
        response.setDiagnose(diagnoseInfo);

        List<PrescriptionDetailResponse.MedicineInfo> medicineInfos = medicines.stream().map(medicine -> {
            PrescriptionDetailResponse.MedicineInfo info = new PrescriptionDetailResponse.MedicineInfo();
            info.setMedicineName(medicine.getMedicineName().getMedicineName());
            info.setMedicationInstruction(medicine.getMedicationInstruction());
            info.setMedicinePrice(medicine.getMedicinePrice());
            info.setQuantity(medicine.getQuantity());
            return info;
        }).collect(Collectors.toList());

        response.setMedicines(medicineInfos);

        return response;
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        return prescriptions.stream().map(p -> {
            String prescriptionId = p.getPrescriptionId();
            String diagnoseId = prescriptionId.substring(0, 8) + prescriptionId.substring(11);
            System.out.println(diagnoseId);
            TreatmentRecord diagnose = treatmentRecordRepository.findById(diagnoseId).orElse(null);
            TreatmentRecord2 treatmentRecord = treatmentRecord2Repository.findById(diagnoseId).orElse(null);

            return new PrescriptionResponse(
                    p.getPrescriptionId(),
                    treatmentRecord != null ? convertToDateTime(treatmentRecord.getDiagnoseTime()) : null,
                    diagnose != null ? diagnose.getPatient().getPatientId() : null,
                    p.getTotalPrice(),
                    p.getPaystate(),
                    p.getDoctor().getDoctorId()
            );
        }).collect(Collectors.toList());
    }

    private LocalDateTime convertToDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public List<PrescriptionMedicine> getPrescriptionMedicines(String prescriptionId) {
        return prescriptionMedicineRepository.findByPrescriptionPrescriptionId(prescriptionId);
    }

    @Transactional
    public Optional<Prescription> updatePayState(String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId).map(prescription -> {
            // 1 表示已支付
            if (prescription.getPaystate() != 1) {
                prescription.setPaystate(1);
                return prescriptionRepository.save(prescription);
            }
            return prescription;
        });
    }
}

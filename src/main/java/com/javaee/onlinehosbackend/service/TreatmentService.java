package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.TreatmentRecordRequest;
import com.javaee.onlinehosbackend.entity.*;
import com.javaee.onlinehosbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.Instant;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;
    @Autowired
    private TreatmentRecord2Repository treatmentRecord2Repository;
    @Autowired
    private TreatmentFeedbackRepository treatmentFeedbackRepository;

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicineSellRepository medicineSellRepository;

    @Autowired
    private MedicineDescriptionRepository medicineDescriptionRepository;

    public List<String> getRecordsByPatientId(String patientId) {
//        System.out.println(patientId);
//        System.out.println(treatmentRepository.findRecordsByEmbeddedPatientId(patientId));
        return treatmentRecord2Repository.findRecordsByEmbeddedPatientId(patientId);
    }

    public List<TreatmentFeedback> getAllFeedbacks() {
        return treatmentFeedbackRepository.findAll();
    }

    public void deleteFeedback(String diagnosedId) throws Exception {
        TreatmentFeedback feedback = treatmentFeedbackRepository.findById(diagnosedId).orElse(null);
        if (feedback == null) {
            throw new Exception("未找到相关评价记录");
        }

        TreatmentRecord2 record = treatmentRecord2Repository.findById(diagnosedId).orElse(null);
        if (record == null) {
            throw new Exception("无对应诊断记录");
        }

        record.setCommentstate(0); // 标记为未评价
        treatmentFeedbackRepository.delete(feedback); // 删除反馈
    }

    @Transactional
    public void createFeedback(String diagnoseId, Integer treatmentScore, String evaluation) throws Exception {
        String patientId = diagnoseId.substring(8, 15); // Start at index 8 and the next 7 characters
        String doctorId = diagnoseId.substring(15, 20); // Start at index 15 and the next 5 characters
        // 确保 TreatmentRecord 存在
        TreatmentRecord treatmentRecord = treatmentRecordRepository.findById(diagnoseId)
                .orElseThrow(() -> new Exception("No such treatment record found"));
        // 检查 TreatmentRecord2 实体的状态并更新
        TreatmentRecord2 treatmentRecord2 = treatmentRecord2Repository.findById(diagnoseId)
                .orElseThrow(() -> new Exception("No such diagnosis record found or it has already been evaluated"));
        if (treatmentRecord2.getCommentstate() == 1) {
            throw new Exception("Diagnosis record has already been evaluated");
        }
        treatmentRecord2.setCommentstate(1); // 设置为已评价
        treatmentRecord2Repository.save(treatmentRecord2);
        treatmentRecordRepository.save(treatmentRecord);
        // 创建 TreatmentFeedback 实体
        TreatmentFeedback feedback = new TreatmentFeedback();
        feedback.setPatientId(patientId); // Extracted patient ID from diagnosedId
        feedback.setTreatmentRecord(treatmentRecord); // 设置 TreatmentRecord
        feedback.setTreatmentScore(treatmentScore);
        feedback.setEvaluation(evaluation);
        feedback.setDoctorId(doctorId); // Extracted doctor ID from diagnosedId
        System.out.println(feedback);
        // 保存 TreatmentFeedback 实体
        treatmentFeedbackRepository.save(feedback);
    }

    @Transactional
    public String createTreatmentRecord(TreatmentRecordRequest request) {
        // 生成诊断记录ID和处方ID
        String diagnoseId = generateDiagnoseId(request);
        System.out.println(diagnoseId);
        String prescriptionId = generatePrescriptionId(request);

        // 获取Doctor和Patient实体
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElse(null);
        Patient patient = patientRepository.findById(request.getPatientId()).orElse(null);

        // 创建TreatmentRecord实体
        TreatmentRecord treatmentRecord = new TreatmentRecord();
        treatmentRecord.setDiagnosisRecordId(diagnoseId);
        treatmentRecord.setDoctor(doctor);
        treatmentRecord.setPatient(patient);
        treatmentRecord = treatmentRecordRepository.save(treatmentRecord);
        // 创建TreatmentRecord2实体
        TreatmentRecord2 treatmentRecord2 = new TreatmentRecord2();
//        treatmentRecord2.setDiagnoseId(diagnoseId);
        treatmentRecord2.setTreatmentRecord(treatmentRecord);
        treatmentRecord2.setDiagnoseTime(Instant.now());
        treatmentRecord2.setCommentstate(0); // 初始评论状态设置为0
        treatmentRecord2.setSelfreported(request.getSelfReported()); // 自述
        treatmentRecord2.setPresenthis(request.getPresentHis()); // 现病史
        treatmentRecord2.setAnamnesis(request.getAnamnesis()); // 既往史
        treatmentRecord2.setSign(request.getSign()); // 体征
        treatmentRecord2.setClinicdia(request.getClinicDia()); // 临床诊断
        treatmentRecord2.setAdvice(request.getAdvice()); // 医嘱
        treatmentRecord2.setKindquantity(0); // 药品种类数量，初始设置为0，后续可能根据实际药品信息更新

        // 创建Prescription实体
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(prescriptionId);
        prescription.setDoctor(doctor);
        prescription.setTotalPrice(new BigDecimal(0)); // 初始化总价格
        prescription.setPaystate(0); // 设置支付状态

        // 处理挂号记录
        Registration registration = registrationRepository
                .findFirstByPatientAndDoctorAndPeriod(patient, doctor, request.getPeriod());
        System.out.println(registration);
        if (registration != null) {
            registration.setState(1); // 更新状态为已就诊
            registration.setPrescriptionid(prescription); // 设置处方ID
        }

        // 解析药品信息并更新处方
        BigDecimal totalPrice = new BigDecimal(0);
        String[] medicines = request.getMedicine().split(";");
        for (String medicine : medicines) {
            System.out.println(medicine);
        }
        for (String medicine : medicines) {
            String[] parts = medicine.split("\\+");
            if (parts.length != 2) continue;

            String[] medicineDetails = parts[0].split("\\*");
            if (medicineDetails.length != 2) continue;

            String medicineName = medicineDetails[0];
            System.out.println(medicineName+"***");
            int quantity;
            try {
                quantity = Integer.parseInt(medicineDetails[1]);
            } catch (NumberFormatException e) {
                continue;
            }
            String medicationInstruction = parts[1];
            System.out.println(medicationInstruction+"***");
            // 从数据库获取药品价格
            MedicineSell medicineSell = medicineSellRepository.findByName(medicineName);
            if (medicineSell == null) continue;

            BigDecimal price = medicineSell.getSellingPrice().multiply(new BigDecimal(quantity));
            totalPrice = totalPrice.add(price);

            PrescriptionMedicineId prescriptionMedicineId = new PrescriptionMedicineId();
            prescriptionMedicineId.setPrescriptionId(prescriptionId);
            prescriptionMedicineId.setMedicineName(medicineName);

            MedicineDescription medicineDescription = medicineDescriptionRepository.findByMedicineName(medicineName);
            if (medicineDescription == null) {
                continue;
            }

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
            prescriptionMedicine.setId(prescriptionMedicineId);
            prescriptionMedicine.setMedicationInstruction(medicationInstruction);
            prescriptionMedicine.setQuantity(quantity);
            prescriptionMedicine.setMedicinePrice(medicineSell.getSellingPrice());
            prescriptionMedicine.setPrescription(prescription);
            prescriptionMedicine.setMedicineName(medicineDescription); // 使用MedicineDescription实例
            prescriptionMedicineRepository.save(prescriptionMedicine);
            treatmentRecord2.setKindquantity(treatmentRecord2.getKindquantity() + 1);
        }

        // 更新Prescription实体的总价格
        prescription.setTotalPrice(totalPrice);

        // 保存所有实体到数据库
        treatmentRecord2Repository.save(treatmentRecord2);
        if (registration != null) {
            System.out.println("******");
            registrationRepository.save(registration);
        }
        prescriptionRepository.save(prescription);
        return "Treatment record created successfully.";
    }

    private String generateDiagnoseId(TreatmentRecordRequest treatmentRecordRequest) {
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + treatmentRecordRequest.getPatientId() + treatmentRecordRequest.getDoctorId() + treatmentRecordRequest.getPeriod();
    }

    private String generatePrescriptionId(TreatmentRecordRequest treatmentRecordRequest) {
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "000" + treatmentRecordRequest.getPatientId() + treatmentRecordRequest.getDoctorId() + treatmentRecordRequest.getPeriod();
    }

    public List<TreatmentRecord2> getPatientRecords(String patientId) {
        List<String> diagnosisRecordIds = treatmentRecordRepository.findDiagnosisRecordIdsByPatientId(patientId);
        return treatmentRecord2Repository.findAllById(diagnosisRecordIds);
    }
}

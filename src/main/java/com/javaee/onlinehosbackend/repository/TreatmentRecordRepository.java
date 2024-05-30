package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.TreatmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, String> {
    @Query("SELECT tr.diagnosisRecordId FROM TreatmentRecord tr WHERE tr.patient.patientId = :patientId")
    List<String> findDiagnosisRecordIdsByPatientId(@Param("patientId") String patientId);

    @Query("SELECT tr FROM TreatmentRecord tr WHERE tr.patient.patientId = :patientId")
    List<TreatmentRecord> findByPatientId(@Param("patientId") String patientId);

    TreatmentRecord findByDiagnosisRecordId(String diagnosisRecordId);
}
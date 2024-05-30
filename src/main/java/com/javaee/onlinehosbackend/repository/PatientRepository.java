package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    // 这里可以添加额外的查询方法，例如根据ID查找病人
    boolean existsByPatientIdAndPassword(String patientId, String password);

    boolean existsByContactAndPatientId(String contact, String patientId);

    boolean existsByPatientId(String patientId);
}

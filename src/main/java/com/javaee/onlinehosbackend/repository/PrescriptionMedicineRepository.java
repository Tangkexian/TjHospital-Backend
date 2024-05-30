package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.PrescriptionMedicine;
import com.javaee.onlinehosbackend.entity.PrescriptionMedicineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, PrescriptionMedicineId> {

    @Query("SELECT pm FROM PrescriptionMedicine pm WHERE pm.id.prescriptionId IN :prescriptionIds")
    List<PrescriptionMedicine> findByPrescriptionIdIn(@Param("prescriptionIds") List<String> prescriptionIds);

    List<PrescriptionMedicine> findByPrescriptionPrescriptionId(String prescriptionId);
}

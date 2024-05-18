package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.MedicineDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineDescriptionRepository extends JpaRepository<MedicineDescription, String> {
    List<MedicineDescription> findByMedicineNameIn(List<String> medicineNames);

    MedicineDescription findByMedicineName(String medicineName);
}

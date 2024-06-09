package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.MedicineStock;
import com.javaee.onlinehosbackend.entity.MedicineStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicineStockRepository extends JpaRepository<MedicineStock, MedicineStockId> {

    @Query("SELECT ms FROM MedicineStock ms WHERE ms.id.medicineName = :medicineName AND ms.id.manufacturer = :manufacturer AND ms.id.productionDate = :productionDate")
    MedicineStock findByMedicineNameAndManufacturerAndProductionDate(@Param("medicineName") String medicineName, @Param("manufacturer") String manufacturer, @Param("productionDate") LocalDate productionDate);

    @Query("SELECT ms FROM MedicineStock ms WHERE ms.id.medicineName = :medicineName AND ms.id.manufacturer = :manufacturer")
    List<MedicineStock> findByMedicineNameAndManufacturer(@Param("medicineName") String medicineName, @Param("manufacturer") String manufacturer);

    List<MedicineStock> findByCleanDateNotNull();

    List<MedicineStock> findByCleanDateIsNull();
}


package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.dto.MedicineStatisticsResponse;
import com.javaee.onlinehosbackend.dto.SellStatistic;
import com.javaee.onlinehosbackend.entity.MedicineOut;
import com.javaee.onlinehosbackend.entity.MedicineOutId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineOutRepository extends JpaRepository<MedicineOut, MedicineOutId> {

    @Query("SELECT new com.javaee.onlinehosbackend.dto.SellStatistic (" +
            "mo.id.medicineName, mo.id.manufacturer, YEAR(mo.id.deliverDate), MONTH(mo.id.deliverDate), " +
            "SUM(mo.id.purchaseAmount), SUM(mo.id.purchaseAmount * ms.sellingPrice)) " +
            "FROM MedicineOut mo " +
            "JOIN MedicineSell ms ON mo.id.medicineName = ms.id.medicineName AND mo.id.manufacturer = ms.id.manufacturer " +
            "GROUP BY mo.id.medicineName, mo.id.manufacturer, YEAR(mo.id.deliverDate), MONTH(mo.id.deliverDate)")
    List<SellStatistic> calculateMonthlySellStatistics();
}




package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.dto.PurchaseStatistic;
import com.javaee.onlinehosbackend.entity.MedicinePurchase;
import com.javaee.onlinehosbackend.entity.MedicinePurchaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicinePurchaseRepository extends JpaRepository<MedicinePurchase, MedicinePurchaseId> {

    @Query("SELECT new com.javaee.onlinehosbackend.dto.PurchaseStatistic(" +
            "mp.id.medicineName, mp.id.manufacturer, YEAR(mp.id.purchaseDate), MONTH(mp.id.purchaseDate), " +
            "SUM(mp.purchaseAmount), SUM(mp.purchaseAmount * mp.purchasePrice)) " +
            "FROM MedicinePurchase mp " +
            "GROUP BY mp.id.medicineName, mp.id.manufacturer, YEAR(mp.id.purchaseDate), MONTH(mp.id.purchaseDate)")
    List<PurchaseStatistic> calculateMonthlyPurchaseStatistics();
}

package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.MedicineSell;
import com.javaee.onlinehosbackend.entity.MedicineSellId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineSellRepository extends JpaRepository<MedicineSell, MedicineSellId> {
    @Query("SELECT m FROM MedicineSell m WHERE m.id.medicineName = :medicineName")
    MedicineSell findByName(String medicineName);

    // 检查特定药品销售记录是否存在
    boolean existsById(MedicineSellId id);

    // 删除特定药品销售记录
    void deleteById(MedicineSellId id);
}

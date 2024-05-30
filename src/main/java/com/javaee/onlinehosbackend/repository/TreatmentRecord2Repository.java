package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.TreatmentRecord2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRecord2Repository extends JpaRepository<TreatmentRecord2, String> {

    // 使用 JPQL 或原生 SQL 查询根据 DIAGNOSE_ID 中的 patientId 部分来查找记录
    @Query("SELECT tr.diagnoseId FROM TreatmentRecord2 tr WHERE SUBSTRING(tr.diagnoseId, 9, 7) = :patientId AND tr.commentstate = 1")
    List<String> findRecordsByEmbeddedPatientId(String patientId);
}

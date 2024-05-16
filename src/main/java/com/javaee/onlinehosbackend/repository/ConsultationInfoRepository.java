package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.ConsultationInfo;
import com.javaee.onlinehosbackend.entity.ConsultationInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
public interface ConsultationInfoRepository extends JpaRepository<ConsultationInfo, ConsultationInfoId> {
    // 根据医生ID获取咨询信息
    @Query("SELECT c FROM ConsultationInfo c WHERE c.doctor.doctorId = :doctorId")
    List<ConsultationInfo> findByDoctorId(String doctorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ConsultationInfo ci WHERE ci.id.doctorId = :doctorId AND ci.id.dateTime = :dateTime AND ci.id.period = :period")
    void deleteByDoctorIdAndDateTimeAndPeriod(@Param("doctorId") String doctorId, @Param("dateTime") Instant dateTime, @Param("period") Integer period);

    @Query("SELECT COUNT(ci) > 0 FROM ConsultationInfo ci WHERE ci.id.doctorId = :doctorId AND ci.id.dateTime = :dateTime AND ci.id.period = :period")
    boolean existsByDoctorIdAndDateTimeAndPeriod(@Param("doctorId") String doctorId, @Param("dateTime") Instant dateTime, @Param("period") Integer period);


    boolean existsById(ConsultationInfoId consultationInfoId);
}

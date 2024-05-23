package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, String> {
    @Query("SELECT la FROM LeaveApplication la WHERE la.leaveNoteId LIKE %:patientId%")
    List<LeaveApplication> findByPatientId(@Param("patientId") String patientId);

    @Query("SELECT SUBSTRING(la.leaveNoteId, 1, LENGTH(la.leaveNoteId) - 3) FROM LeaveApplication la WHERE la.leaveNoteId LIKE %:patientId%")
    List<String> findDiagnosedIdsByPatientId(@Param("patientId") String patientId);

    @Query("SELECT la.leaveNoteId FROM LeaveApplication la WHERE la.leaveNoteId LIKE %:patientId% AND la.leaveNoteRemark = '通过'")
    List<String> findAuditedLeaveNoteIdsByPatientId(@Param("patientId") String patientId);

    @Query("SELECT la FROM LeaveApplication la WHERE la.leaveNoteId LIKE %:doctorId% AND la.leaveNoteRemark = '未审核'")
    List<LeaveApplication> findUnauditedLeaveApplicationsByDoctorId(@Param("doctorId") String doctorId);
}

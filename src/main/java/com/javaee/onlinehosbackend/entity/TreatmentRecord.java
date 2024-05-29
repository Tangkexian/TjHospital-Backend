package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "TREATMENT_RECORD", indexes = {
        @Index(name = "DOCTOR_ID", columnList = "DOCTOR_ID"),
        @Index(name = "PATIENT_ID", columnList = "PATIENT_ID"),
        @Index(name = "LEAVE_NOTE_ID", columnList = "LEAVE_NOTE_ID")
})
public class TreatmentRecord {
    @Id
    @Column(name = "DIAGNOSIS_RECORD_ID", nullable = false, length = 30)
    private String diagnosisRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LEAVE_NOTE_ID")
    private LeaveApplication leaveNote;

}

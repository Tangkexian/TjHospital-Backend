package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "TREATMENT_FEEDBACK")
public class TreatmentFeedback {
    @Id
    @Column(name = "DIAGNOSEDID", nullable = false, length = 40)
    private String diagnosedid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DIAGNOSEDID", nullable = false)
    private TreatmentRecord treatmentRecord;

    @Column(name = "PATIENT_ID", nullable = false, length = 20)
    private String patientId;

    @Column(name = "DOCTOR_ID", nullable = false, length = 20)
    private String doctorId;

    @Column(name = "TREATMENT_SCORE")
    private Integer treatmentScore;

    @Column(name = "EVALUATION", length = 200)
    private String evaluation;

}
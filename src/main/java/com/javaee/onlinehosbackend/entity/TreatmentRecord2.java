package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TREATMENT_RECORD2")
public class TreatmentRecord2 {
    @Id
    @Column(name = "DIAGNOSE_ID", nullable = false, length = 30)
    private String diagnoseId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DIAGNOSE_ID", nullable = false)
    private TreatmentRecord treatmentRecord;

    @Column(name = "DIAGNOSE_TIME")
    private Instant diagnoseTime;

    @Column(name = "COMMENTSTATE")
    private Integer commentstate;

    @Column(name = "SELFREPORTED", length = 2000)
    private String selfreported;

    @Column(name = "PRESENTHIS", length = 2000)
    private String presenthis;

    @Column(name = "ANAMNESIS", length = 2000)
    private String anamnesis;

    @Column(name = "SIGN", length = 1000)
    private String sign;

    @Column(name = "CLINICDIA", length = 2000)
    private String clinicdia;

    @Column(name = "ADVICE", length = 2000)
    private String advice;

    @Column(name = "KINDQUANTITY")
    private Integer kindquantity;

}
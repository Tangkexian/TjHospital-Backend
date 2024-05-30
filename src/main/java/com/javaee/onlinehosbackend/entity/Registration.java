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
@Table(name = "REGISTRATION")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTRATION_ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @Column(name = "APPOINTMENT_TIME", nullable = false)
    private Instant appointmentTime;

    @Column(name = "PERIOD", nullable = false)
    private Integer period;

    @Column(name = "REGISTORDER")
    private Integer registorder;

    @Column(name = "STATE", nullable = false)
    private Integer state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRESCRIPTIONID")
    private Prescription prescriptionid;

    @Column(name = "CHECKIN", nullable = false)
    private Integer checkin;

    @Column(name = "QRCODEURL", length = 2000)
    private String qrcodeurl;

    @Column(name = "ORDERTIME")
    private Instant ordertime;

}
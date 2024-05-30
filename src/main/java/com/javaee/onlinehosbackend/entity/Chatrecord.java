package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CHATRECORD", indexes = {
        @Index(name = "PATIENT_ID", columnList = "PATIENT_ID")
})
public class Chatrecord {
    @EmbeddedId
    private ChatrecordId id;

    @MapsId("doctorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @MapsId("patientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @Column(name = "RECORDID", nullable = false, length = 30)
    private String recordid;

    @Column(name = "MESSAGE", nullable = false, length = 2000)
    private String message;

    @Column(name = "SENDER_TYPE", nullable = false)
    private Integer senderType;

    @Column(name = "READ_STATUS", nullable = false)
    private Integer readStatus;

}
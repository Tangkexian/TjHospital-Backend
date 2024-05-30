package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "OUTPATIENT_ORDER", indexes = {
        @Index(name = "PATIENT_ID", columnList = "PATIENT_ID")
})
public class OutpatientOrder {
    @Id
    @Column(name = "ORDER_ID", nullable = false, length = 30)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @Column(name = "ORDER_TIME", nullable = false)
    private Instant orderTime;

}
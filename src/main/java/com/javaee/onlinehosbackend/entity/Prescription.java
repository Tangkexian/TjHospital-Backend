package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRESCRIPTION", indexes = {
        @Index(name = "DOCTOR_ID", columnList = "DOCTOR_ID")
})
public class Prescription {
    @Id
    @Column(name = "PRESCRIPTION_ID", nullable = false, length = 40)
    private String prescriptionId;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 6, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @Column(name = "PAYSTATE", nullable = false)
    private Integer paystate;

}
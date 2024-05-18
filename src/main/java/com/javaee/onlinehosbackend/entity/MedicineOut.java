package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MEDICINE_OUT", indexes = {
        @Index(name = "PATIENT_ID", columnList = "PATIENT_ID")
})
public class MedicineOut {
    @EmbeddedId
    private MedicineOutId id;

    @MapsId("patientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

}
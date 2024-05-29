package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRESCRIPTION_MEDICINE", indexes = {
        @Index(name = "MEDICINE_NAME", columnList = "MEDICINE_NAME")
})
public class PrescriptionMedicine {
    @EmbeddedId
    private PrescriptionMedicineId id;

    @MapsId("prescriptionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PRESCRIPTION_ID", nullable = false)
    private Prescription prescription;

    @MapsId("medicineName")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEDICINE_NAME", nullable = false)
    private MedicineDescription medicineName;

    @Column(name = "MEDICATION_INSTRUCTION", nullable = false, length = 800)
    private String medicationInstruction;

    @Column(name = "MEDICINE_PRICE", nullable = false, precision = 6, scale = 2)
    private BigDecimal medicinePrice;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

}

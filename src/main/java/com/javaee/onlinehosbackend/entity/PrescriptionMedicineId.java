package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PrescriptionMedicineId implements Serializable {
    private static final long serialVersionUID = -3509516859987394386L;
    @Column(name = "PRESCRIPTION_ID", nullable = false, length = 40)
    private String prescriptionId;

    @Column(name = "MEDICINE_NAME", nullable = false, length = 200)
    private String medicineName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PrescriptionMedicineId entity = (PrescriptionMedicineId) o;
        return Objects.equals(this.prescriptionId, entity.prescriptionId) &&
                Objects.equals(this.medicineName, entity.medicineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, medicineName);
    }

}
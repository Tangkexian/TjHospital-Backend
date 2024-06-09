package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MedicineOutId implements Serializable {
    private static final long serialVersionUID = 8343555478784902286L;
    @Column(name = "MEDICINE_NAME", nullable = false, length = 40)
    private String medicineName;

    @Column(name = "MANUFACTURER", nullable = false, length = 40)
    private String manufacturer;

    @Column(name = "PRODUCTION_DATE", nullable = false)
    private LocalDate productionDate;

    @Column(name = "PURCHASE_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchaseAmount;

    @Column(name = "DELIVER_DATE", nullable = false)
    private Instant deliverDate;

    @Column(name = "PATIENT_ID", nullable = false, length = 20)
    private String patientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MedicineOutId entity = (MedicineOutId) o;
        return Objects.equals(this.productionDate, entity.productionDate) &&
                Objects.equals(this.patientId, entity.patientId) &&
                Objects.equals(this.deliverDate, entity.deliverDate) &&
                Objects.equals(this.medicineName, entity.medicineName) &&
                Objects.equals(this.manufacturer, entity.manufacturer) &&
                Objects.equals(this.purchaseAmount, entity.purchaseAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionDate, patientId, deliverDate, medicineName, manufacturer, purchaseAmount);
    }

}
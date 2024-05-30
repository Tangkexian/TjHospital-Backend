package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MedicinePurchaseId implements Serializable {
    private static final long serialVersionUID = -7822425792636840428L;
    @Column(name = "MEDICINE_NAME", nullable = false, length = 40)
    private String medicineName;

    @Column(name = "MANUFACTURER", nullable = false, length = 460)
    private String manufacturer;

    @Column(name = "PRODUCTION_DATE", nullable = false)
    private LocalDate productionDate;

    @Column(name = "PURCHASE_DATE", nullable = false)
    private LocalDate purchaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MedicinePurchaseId entity = (MedicinePurchaseId) o;
        return Objects.equals(this.purchaseDate, entity.purchaseDate) &&
                Objects.equals(this.productionDate, entity.productionDate) &&
                Objects.equals(this.medicineName, entity.medicineName) &&
                Objects.equals(this.manufacturer, entity.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseDate, productionDate, medicineName, manufacturer);
    }

}
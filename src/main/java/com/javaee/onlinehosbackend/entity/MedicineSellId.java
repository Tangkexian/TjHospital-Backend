package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MedicineSellId implements Serializable {
    private static final long serialVersionUID = -6483159598217319938L;
    @Column(name = "MEDICINE_NAME", nullable = false, length = 40)
    private String medicineName;

    @Column(name = "MANUFACTURER", nullable = false, length = 80)
    private String manufacturer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MedicineSellId entity = (MedicineSellId) o;
        return Objects.equals(this.medicineName, entity.medicineName) &&
                Objects.equals(this.manufacturer, entity.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineName, manufacturer);
    }

}
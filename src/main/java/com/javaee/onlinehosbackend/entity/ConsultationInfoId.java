package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ConsultationInfoId implements Serializable {
    private static final long serialVersionUID = -1822417099362462417L;
    @Column(name = "DOCTOR_ID", nullable = false, length = 10)
    private String doctorId;

    @Column(name = "CLINIC_NAME", nullable = false, length = 200)
    private String clinicName;

    @Column(name = "DATE_TIME", nullable = false)
    private Instant dateTime;

    @Column(name = "PERIOD", nullable = false)
    private Integer period;

    public ConsultationInfoId() {

    }
    public ConsultationInfoId(String doctorId, String clinicName, Instant dateTime, Integer period) {
        this.doctorId = doctorId;
        this.clinicName = clinicName;
        this.dateTime = dateTime;
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConsultationInfoId entity = (ConsultationInfoId) o;
        return Objects.equals(this.clinicName, entity.clinicName) &&
                Objects.equals(this.dateTime, entity.dateTime) &&
                Objects.equals(this.period, entity.period) &&
                Objects.equals(this.doctorId, entity.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicName, dateTime, period, doctorId);
    }

}
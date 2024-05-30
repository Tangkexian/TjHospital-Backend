package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ChatrecordId implements Serializable {
    private static final long serialVersionUID = 6301899482193266293L;
    @Column(name = "DOCTOR_ID", nullable = false, length = 20)
    private String doctorId;

    @Column(name = "PATIENT_ID", nullable = false, length = 20)
    private String patientId;

    @Column(name = "TIMESTAMP", columnDefinition = "timestamp(6) not null")
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatrecordId entity = (ChatrecordId) o;
        return Objects.equals(this.doctorId, entity.doctorId) &&
                Objects.equals(this.patientId, entity.patientId) &&
                Objects.equals(this.timestamp, entity.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, patientId, timestamp);
    }

}
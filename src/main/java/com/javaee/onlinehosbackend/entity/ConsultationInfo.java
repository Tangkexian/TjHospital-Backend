package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author WWWsy
 */
@Getter
@Setter
@Entity
@Table(name = "CONSULTATION_INFO", indexes = {
        @Index(name = "CLINIC_NAME", columnList = "CLINIC_NAME")
})
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationInfo {
    @EmbeddedId
    private ConsultationInfoId id;

    @MapsId("doctorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @MapsId("clinicName")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CLINIC_NAME", nullable = false)
    private ConsultingRoom clinicName;

}
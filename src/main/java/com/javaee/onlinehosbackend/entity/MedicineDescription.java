package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MEDICINE_DESCRIPTION")
public class MedicineDescription {
    @Id
    @Column(name = "MEDICINE_NAME", nullable = false, length = 100)
    private String medicineName;

    @Column(name = "MEDICINE_TYPE", nullable = false, length = 40)
    private String medicineType;

    @Column(name = "APPLICABLE_SYMPTOM", length = 600)
    private String applicableSymptom;

    @Column(name = "VULGO", length = 200)
    private String vulgo;

    @Column(name = "SPECIFICATION", length = 500)
    private String specification;

    @Column(name = "SINGLEDOSE", length = 300)
    private String singledose;

    @Column(name = "ADMINISTRATION", length = 400)
    private String administration;

    @Column(name = "ATTENTION", length = 400)
    private String attention;

    @Column(name = "FREQUENCY", length = 200)
    private String frequency;

}
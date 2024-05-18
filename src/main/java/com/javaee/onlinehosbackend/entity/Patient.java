package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PATIENT")
public class Patient {
    @Id
    @Column(name = "PATIENT_ID", nullable = false, length = 20)
    private String patientId;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "GENDER")
    private Boolean gender;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "CONTACT", length = 20)
    private String contact;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "COLLEGE", nullable = false, length = 40)
    private String college;

    @Column(name = "COUNSELLOR", nullable = false, length = 20)
    private String counsellor;

}
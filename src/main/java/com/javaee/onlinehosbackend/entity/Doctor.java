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
@Table(name = "DOCTOR")
public class Doctor {
    @Id
    @Column(name = "DOCTOR_ID", nullable = false, length = 20)
    private String doctorId;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "GENDER")
    private Boolean gender;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "TITLE", length = 20)
    private String title;

    @Column(name = "CONTACT", length = 20)
    private String contact;

    @Column(name = "SECONDARY_DEPARTMENT", length = 50)
    private String secondaryDepartment;

    @Column(name = "PHOTOURL", length = 600)
    private String photourl;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "SKILLEDIN", length = 1000)
    private String skilledin;

}
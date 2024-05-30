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
@Table(name = "TEMPLATE")
public class Template {
    @Id
    @Column(name = "NAME", nullable = false, length = 40)
    private String name;

    @Column(name = "PROBLEM", length = 100)
    private String problem;

    @Column(name = "ILLNESS", length = 50)
    private String illness;

    @Column(name = "COLUMN1", length = 50)
    private String column1;

    @Column(name = "SYMPTOM", length = 50)
    private String symptom;

    @Column(name = "DIAGNOSE", length = 30)
    private String diagnose;

    @Column(name = "PRESCRIPTION", length = 20)
    private String prescription;

    @Column(name = "MEDICINE", length = 70)
    private String medicine;

}
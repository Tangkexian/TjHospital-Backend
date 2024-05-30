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
@Table(name = "ADMINISTRATOR")
public class Administrator {
    @Id
    @Column(name = "ADMINISTRATOR_ID", nullable = false, length = 20)
    private String administratorId;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "GENDER")
    private Boolean gender;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "CONTACT", length = 20)
    private String contact;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

}
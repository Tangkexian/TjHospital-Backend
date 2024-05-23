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
@Table(name = "DEPARTMENT2")
public class Department2 {
    @Id
    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 80)
    private String departmentName;

    @Column(name = "DEPARTMENT_DESCRIPTION", length = 800)
    private String departmentDescription;

}
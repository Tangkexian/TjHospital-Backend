package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
}

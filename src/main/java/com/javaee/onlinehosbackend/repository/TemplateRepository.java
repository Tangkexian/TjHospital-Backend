package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
} 

